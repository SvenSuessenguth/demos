package org.cc.testing.mprestclient;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class AgifyMessageBodyReader implements MessageBodyReader<AgifyResponse> {
  @Override
  public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
    return AgifyResponse.class.isAssignableFrom(aClass);
  }

  @Override
  public AgifyResponse readFrom(Class<AgifyResponse> aClass, Type type, Annotation[] annotations, MediaType mediaType,
                                MultivaluedMap<String, String> multivaluedMap, InputStream inputStream)
    throws WebApplicationException {

    JsonReaderFactory factory = Json.createReaderFactory(null);
    try (JsonReader reader = factory.createReader(inputStream)) {
      JsonObject jsonObject = reader.readObject();

      var alterInJahren = jsonObject.getInt("age");
      var anzahlAnfragen = jsonObject.getInt("count");
      var name = jsonObject.getString("name");

      var agifyResponse = new AgifyResponse();
      agifyResponse.setAlterInJahren(alterInJahren);
      agifyResponse.setAnzahlAnfragen(anzahlAnfragen);
      agifyResponse.setName(name);

      return agifyResponse;
    } catch (Exception e) {
      throw new WebApplicationException(e);
    }
  }
}
