package org.cc.testing.mprestclient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient
@RegisterClientHeaders(AgifyClientHeadersFactory.class)
@RegisterProvider(AgifyMessageBodyReader.class)
public interface AgifyClient {
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  AgifyResponse agify(@QueryParam("name") String name);
}

