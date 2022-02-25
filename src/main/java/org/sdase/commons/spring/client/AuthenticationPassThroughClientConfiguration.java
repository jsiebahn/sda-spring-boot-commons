/*
* Copyright (c). SDA SE Open Industry Solutions (https://www.sda.se).
*
* All rights reserved.
*/
package org.sdase.commons.spring.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class AuthenticationPassThroughClientConfiguration {

  @Bean
  public RequestInterceptor authHeaderClientInterceptor() {
    return new AuthHeaderClientInterceptor();
  }
}
