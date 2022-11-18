/*
 * Copyright 2022- SDA SE Open Industry Solutions (https://www.sda.se)
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package org.sdase.commons.cacertificates.ssl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Optional;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.bouncycastle.openssl.PEMParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompositeX509TrustManagerTest {

  public static final String AUTH_TYPE = "Basic";
  private X509TrustManager trustManager;
  private X509Certificate trustedChain;
  private X509Certificate unTrustedChain;

  @BeforeEach
  void setUp() throws CertificateException, IOException {
    trustManager = createTrustManager();

    trustedChain = getCertificateFromPemFile("trusted.pem");
    unTrustedChain = getCertificateFromPemFile("untrusted.pem");
  }

  @Test
  void shouldCreateCompositeTrustManager() {
    CompositeX509TrustManager compositeX509TrustManager =
        new CompositeX509TrustManager(Collections.singletonList(trustManager));

    assertThat(compositeX509TrustManager).isNotNull();
  }

  @Test
  void shouldTrustServer() {
    CompositeX509TrustManager compositeX509TrustManager =
        new CompositeX509TrustManager(Collections.singletonList(trustManager));

    assertThatNoException()
        .isThrownBy(
            () -> trustManager.checkServerTrusted(new X509Certificate[] {trustedChain}, AUTH_TYPE));

    assertThatNoException()
        .isThrownBy(
            () ->
                compositeX509TrustManager.checkServerTrusted(
                    new X509Certificate[] {trustedChain}, AUTH_TYPE));
  }

  @Test
  void shouldTrustClient() {
    CompositeX509TrustManager compositeX509TrustManager =
        new CompositeX509TrustManager(Collections.singletonList(trustManager));

    assertThatNoException()
        .isThrownBy(
            () -> trustManager.checkClientTrusted(new X509Certificate[] {trustedChain}, AUTH_TYPE));
    assertThatNoException()
        .isThrownBy(
            () ->
                compositeX509TrustManager.checkClientTrusted(
                    new X509Certificate[] {trustedChain}, AUTH_TYPE));
  }

  @Test
  void shouldNotTrustClient() {
    CompositeX509TrustManager compositeX509TrustManager =
        new CompositeX509TrustManager(Collections.singletonList(trustManager));

    assertThatCode(
            () ->
                trustManager.checkServerTrusted(new X509Certificate[] {unTrustedChain}, AUTH_TYPE))
        .hasMessageContaining("unable to find valid certification path to requested target");
    assertThatExceptionOfType(CertificateException.class)
        .isThrownBy(
            () ->
                compositeX509TrustManager.checkServerTrusted(
                    new X509Certificate[] {unTrustedChain}, AUTH_TYPE));
  }

  @Test
  void shouldNotTrustServer() {
    CompositeX509TrustManager compositeX509TrustManager =
        new CompositeX509TrustManager(Collections.singletonList(trustManager));

    assertThatExceptionOfType(CertificateException.class)
        .isThrownBy(
            () ->
                trustManager.checkServerTrusted(new X509Certificate[] {unTrustedChain}, AUTH_TYPE));
    assertThatExceptionOfType(CertificateException.class)
        .isThrownBy(
            () ->
                compositeX509TrustManager.checkServerTrusted(
                    new X509Certificate[] {unTrustedChain}, AUTH_TYPE));
  }

  @Test
  void shouldHaveSameAcceptedIssuers() {
    CompositeX509TrustManager compositeX509TrustManager =
        new CompositeX509TrustManager(Collections.singletonList(trustManager));

    assertThat(trustManager.getAcceptedIssuers()[0].getIssuerX500Principal())
        .isEqualTo(compositeX509TrustManager.getAcceptedIssuers()[0].getIssuerX500Principal());
  }

  private static X509Certificate getCertificateFromPemFile(String pemPath)
      throws CertificateException, IOException {
    return SslUtil.parseCert(new PEMParser(new StringReader(TestUtil.readPemContent(pemPath))));
  }

  private X509TrustManager createTrustManager() {
    CertificateReader certificateReader =
        new CertificateReader(Paths.get("src", "test", "resources").toString().concat("/notEmpty"));
    Optional<String> pemContent = certificateReader.readCertificates();

    String defaultTrustManagerAlgorithm = TrustManagerFactory.getDefaultAlgorithm();

    KeyStore truststoreFromPemKey = SslUtil.createTruststoreFromPemKey(pemContent.get());

    return SslUtil.getTrustManager(defaultTrustManagerAlgorithm, truststoreFromPemKey);
  }
}
