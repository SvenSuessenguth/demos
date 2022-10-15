package org.cc.samples.mprestclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;

@RequestScoped
public class AgifyService {

  @Inject
  private AgifyClient agifyClient;

  public Integer agify(String name) {
    Response agifyResponse = agifyClient.agify(name);
    JsonReader jsonReader = Json.createReader(new StringReader(agifyResponse.readEntity(String.class)));
    JsonObject jsonObject = jsonReader.readObject();

    return jsonObject.getInt("age");
  }
}
