/*
* Copyright (c). SDA SE Open Industry Solutions (https://www.sda.se).
*
* All rights reserved.
*/
package org.sdase.commons.spring.boot.web.testing.auth.opa;

public interface RequestMethodBuilder {
  RequestPathBuilder withHttpMethod(String httpMethod);
}