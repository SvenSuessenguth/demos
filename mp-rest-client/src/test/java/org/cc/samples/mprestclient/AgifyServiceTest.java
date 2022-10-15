package org.cc.samples.mprestclient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ArquillianExtension.class)
public class AgifyServiceTest {

  @Deployment()
  @SuppressWarnings("unused")
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, "mp-rest-client.jar")
        .addClass(AgifyService.class)
        .addClass(AgifyClient.class)
        .addClass(AgifyApplication.class)
        .addAsResource("META-INF/microprofile-config.properties");
  }

  @Inject
  @RestClient
  private AgifyClient agifyClient;

  @Inject
  private AgifyService agifyService;

  @Test
  void agifyClient() {
    try (Response agifyResponse = agifyClient.agify("Sven")) {
      var entity = agifyResponse.readEntity(String.class);
      assertNotNull(entity);
    }
  }

  @Test
  void agifyService() {
    var age = agifyService.agify("Sabine");
    assertNotNull(age);
  }
}