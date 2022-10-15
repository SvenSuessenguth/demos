package org.cc.samples.mprestclient;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AgifyServiceTest {

  @Deployment()
  public static WebArchive createDeployment() {
    final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "mp-rest-client.war")
        .addClass(AgifyService.class)
        .addClass(AgifyClient.class)
        .addClass(AgifyApplication.class)
        .addAsResource("META-INF/microprofile-config.properties");
    return webArchive;
  }

  @Inject
  @RestClient
  private AgifyClient agifyClient;

  @Inject
  private AgifyService agifyService;

  @Test
  public void agify() {
    Response agifyResponse = agifyClient.agify("Sven");
    System.out.println("#########################################################");
    System.out.println(agifyResponse.readEntity(String.class));
    System.out.println("#########################################################");
    System.out.println(agifyService.agify("Sabine"));
    System.out.println("#########################################################");
  }
}