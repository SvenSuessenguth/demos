package org.cc.testing.mprestclient;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

public class AgifyClientHeadersFactory implements ClientHeadersFactory {
  @Override
  public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
    var headers = new MultivaluedHashMap<String, String>();
    headers.putSingle("Cache-Control", "no-cache");

    return headers;
  }
}
