package org.cc.testing.testcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class TestcontainersIT {

  @Container
  public final GenericContainer<?> openLiberty = new GenericContainer<>(
    new ImageFromDockerfile("testcontainers-image")
      .withDockerfile(Path.of(System.getProperty("user.dir"), "Dockerfile")))
    .waitingFor(Wait.forHttp("/api/resources/ping"))
    .withExposedPorts(9080);


  @Test
  void test() throws Exception {
    assertThat(openLiberty.isRunning()).isTrue();

    var address = openLiberty.getHost();
    var port = openLiberty.getFirstMappedPort();

    var client = HttpClient.newBuilder().build();
    var request = HttpRequest.newBuilder().uri(URI.create("http://" + address + ":" + port + "/api/resources/localdatetime")).build();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertThat(response.body()).isNotNull();
  }
}
