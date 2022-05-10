/*
* Copyright (c). SDA SE Open Industry Solutions (https://www.sda.se).
*
* All rights reserved.
*/
package org.sdase.commons.spring.boot.web.client.oidc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@RestController
public class ClientCredentialsTestApp {

  @Autowired ClientCredentialsFlowFeignClient clientCredentialsFlowFeignClient;

  @GetMapping("/ping/external")
  public void ping() {
    clientCredentialsFlowFeignClient.pong();
  }
}
