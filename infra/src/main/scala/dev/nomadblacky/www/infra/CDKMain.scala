package dev.nomadblacky.www.infra

import software.amazon.awscdk.core._
import software.amazon.awscdk.services.certificatemanager.{
  DnsValidatedCertificate,
  DnsValidatedCertificateProps,
  ICertificate
}
import software.amazon.awscdk.services.cloudfront._
import software.amazon.awscdk.services.route53._
import software.amazon.awscdk.services.route53.targets.{BucketWebsiteTarget, CloudFrontTarget}
import software.amazon.awscdk.services.s3.deployment.{BucketDeployment, BucketDeploymentProps, Source}
import software.amazon.awscdk.services.s3.{Bucket, BucketProps, RedirectProtocol, RedirectTarget}

object CDKMain {
  def main(args: Array[String]): Unit = {
    val app = new App()
    val env = Environment
      .builder()
      .account(sys.env("CDK_ACCOUNT"))
      .region("us-east-1")
      .build()

    val certStack = new CertificateStack(
      app,
      "CertificateStack",
      StackProps.builder().env(env).build()
    )
    new WWWStack(app, "WWWStack", certStack, StackProps.builder().env(env).build())

    app.synth()
  }
}

class CertificateStack(scope: Construct, name: String, props: StackProps) extends Stack(scope, name, props) {
  val hostedZone: IHostedZone = HostedZone.fromLookup(
    this,
    "PublicHostedZone",
    HostedZoneProviderProps.builder().domainName("nomadblacky.dev").build()
  )

  val certificate: ICertificate = new DnsValidatedCertificate(
    this,
    "Certificate",
    DnsValidatedCertificateProps
      .builder()
      .hostedZone(hostedZone)
      .domainName("nomadblacky.dev")
      .subjectAlternativeNames(list("*.nomadblacky.dev"))
      .build()
  )
}

class WWWStack(scope: Construct, id: String, certStack: CertificateStack, props: StackProps)
    extends Stack(scope, id, props) {
  val rootRedirectBucket: Bucket =
    new Bucket(
      this,
      "RootRedirectBucket",
      BucketProps
        .builder()
        .bucketName("nomadblacky.dev")
        .publicReadAccess(true)
        .websiteRedirect(
          RedirectTarget.builder().hostName("www.nomadblacky.dev").protocol(RedirectProtocol.HTTPS).build()
        )
        .build()
    )

  val websiteBucket: Bucket =
    new Bucket(
      this,
      "WebsiteBucket",
      BucketProps
        .builder()
        .bucketName("www.nomadblacky.dev")
        .publicReadAccess(true)
        .websiteIndexDocument("index.html")
        .build()
    )

  val distribution: CloudFrontWebDistribution = {
    val originConfigs = list(
      SourceConfiguration
        .builder()
        .s3OriginSource(S3OriginConfig.builder().s3BucketSource(websiteBucket).build())
        .behaviors(list(Behavior.builder().isDefaultBehavior(true).build()))
        .build()
    )
    val viewerCertificate = ViewerCertificate.fromAcmCertificate(
      certStack.certificate,
      ViewerCertificateOptions.builder().aliases(list("nomadblacky.dev", "www.nomadblacky.dev")).build()
    )
    new CloudFrontWebDistribution(
      this,
      "distribution",
      CloudFrontWebDistributionProps
        .builder()
        .originConfigs(originConfigs)
        .viewerCertificate(viewerCertificate)
        .build()
    )
  }

  val dnsARecordToCloudFrontBucket: ARecord =
    new ARecord(
      this,
      "DnsARecordToCloudFrontBucket",
      ARecordProps
        .builder()
        .zone(certStack.hostedZone)
        .recordName("www.nomadblacky.dev")
        .target(RecordTarget.fromAlias(new CloudFrontTarget(distribution)))
        .build()
    )

  val dnsARecordToRedirectBucket = new ARecord(
    this,
    "DnsARecordToRedirectBucket",
    ARecordProps
      .builder()
      .zone(certStack.hostedZone)
      .recordName("nomadblacky.dev")
      .target(RecordTarget.fromAlias(new BucketWebsiteTarget(rootRedirectBucket)))
      .build()
  )

  val websiteDeployment =
    new BucketDeployment(
      this,
      "WebsiteDeployment",
      BucketDeploymentProps.builder().sources(list(Source.asset("./public"))).destinationBucket(websiteBucket).build()
    )
}
