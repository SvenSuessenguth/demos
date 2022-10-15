package org.cc.samples.mprestclient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ArquillianExtension.class)
public class AgifyServiceTest {

  @Deployment()
  @SuppressWarnings("unused")
  public static WebArchive createDeployment() {
    return ShrinkWrap.create(WebArchive.class, "mp-rest-client.war")
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
  public void agifyClient() {
    try (Response agifyResponse = agifyClient.agify("Sven")) {
      String entity = agifyResponse.readEntity(String.class);
      assertNotNull(entity);
    }
  }

  @Test
  public void agifyService() {
    Integer age = agifyService.agify("Sabine");
    assertNotNull(age);
  }
}