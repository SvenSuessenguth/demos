package org.cc.samples.mprestclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;

@RequestScoped
public class AgifyService {

  @Inject
  private AgifyClient agifyClient;

  public Integer agify(String name) {
    try (Response agifyResponse = agifyClient.agify(name)) {
      var jsonReader = Json.createReader(new StringReader(agifyResponse.readEntity(String.class)));
      var jsonObject = jsonReader.readObject();

      return jsonObject.getInt("age");
    }
  }
}
