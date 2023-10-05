package trivy

import data.lib.trivy

default ignore = false

ignore_cves := {
  # Netty is affected if hostnames are not verified; Netty can be used as asynchronous HTTP client
  # by S3. Since we use the default synchronous HTTP client, we are not affected.
  # Sources
  # - https://github.com/jeremylong/DependencyCheck/issues/5912
  # - https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/http-configuration.html
  "CVE-2023-4586"
}

ignore {
  input.VulnerabilityID == ignore_cves[_]
}
