package org.cc.samples.mprestclient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AgifyServiceTest {

  @Deployment()
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
      assertFalse(entity.isEmpty());
    }
  }

  @Test
  public void agifyService() {
    Integer age = agifyService.agify("Sabine");
    assertNotNull(age);
  }
}