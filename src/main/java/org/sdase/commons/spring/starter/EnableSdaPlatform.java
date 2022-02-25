/*
* Copyright (c). SDA SE Open Industry Solutions (https://www.sda.se).
*
* All rights reserved.
*/
package org.sdase.commons.spring.starter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sdase.commons.spring.async.EnableSdaAsyncWithRequestContext;
import org.sdase.commons.spring.auth.EnableSdaSecurity;
import org.sdase.commons.spring.client.EnableSdaClients;
import org.sdase.commons.spring.docs.EnableSdaDocs;
import org.sdase.commons.spring.jackson.EnableSdaRestGuide;
import org.sdase.commons.spring.monitoring.EnableSdaMonitoring;
import org.sdase.commons.spring.security.ssl.SslContextConfigurator;
import org.springframework.context.annotation.Import;

/**
 * Enables features that make a Spring Boot service compliant with the <a
 * href="https://sda.dev/core-concepts/">SDA SE Core Concepts</a>.
 *
 * <p>So far this includes
 *
 * <ul>
 *   <li>{@linkplain EnableSdaRestGuide requirements of the RESTful API Guide}
 *   <li>{@linkplain EnableSdaDocs live documentation as OpenAPI 3}
 *   <li>{@linkplain EnableSdaSecurity requirements of authentication and authorization}
 *   <li>{@linkplain EnableSdaClients REST clients configured for the SDA Platform}
 *   <li>{@linkplain EnableSdaAsyncWithRequestContext keep the request context in async execution}
 *   <li>automatic configuration of trusted certificate authorities for outgoing connections by
 *       mounting a CA bundle to {@value
 *       org.sdase.commons.spring.security.ssl.SslContextConfigurator#DEFAULT_CA_PATH}
 *   <li>{@linkplain EnableSdaMonitoring monitoring requirements of the SDA Platform}
 * </ul>
 *
 * Additionally, the default context path is configured as {@code /api} to provide consistent
 * resource paths in the SDA Platform. If special cases require to change this, {@code
 * server.servlet.context-path} can be overridden in the {@code application.yaml}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableSdaDocs
@EnableSdaRestGuide
@EnableSdaSecurity
@EnableSdaClients
@EnableSdaAsyncWithRequestContext
@EnableSdaMonitoring
@Import({SslContextConfigurator.class, SdaSpringConfiguration.class})
public @interface EnableSdaPlatform {}
