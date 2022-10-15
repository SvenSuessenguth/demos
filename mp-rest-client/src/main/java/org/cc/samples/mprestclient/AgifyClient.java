package org.cc.samples.mprestclient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
public interface AgifyClient {

  @GET
  Response agify(@QueryParam("name") String name);
}
