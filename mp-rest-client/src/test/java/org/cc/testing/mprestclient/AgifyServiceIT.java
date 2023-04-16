package org.cc.testing.mprestclient;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MicroprofileConfigExtension.class)
class AgifyServiceIT {

  @ConfigProperty(name = "agify-api/mp-rest/url")
  String agifyApiUriString;

  @Test
  void test() throws Exception {
    var agifyApiUri = new URI(agifyApiUriString);

    AgifyClient agifyClient = RestClientBuilder.newBuilder()
      .baseUri(agifyApiUri)
      .build(AgifyClient.class);
    var agifyResponse = agifyClient.agify("Sven");

    assertThat(agifyResponse.getName()).isEqualTo("Sven");
    assertThat(agifyResponse.getAlterInJahren()).isEqualTo(47);
    assertThat(agifyResponse.getAnzahlAnfragen()).isPositive();
  }
}