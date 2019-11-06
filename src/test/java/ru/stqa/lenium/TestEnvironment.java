package ru.stqa.lenium;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.net.PortProber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

class TestEnvironment {

  private File tfs;
  private int port;
  private Undertow server;

  TestEnvironment() {
    tfs = TemporaryFilesystem.getDefaultTmpFS().createTempDir("lenium", "tests");
    port = PortProber.findFreePort();
    server = Undertow.builder()
      .addHttpListener(port, "localhost")
      .setHandler(new ResourceHandler(new PathResourceManager(tfs.toPath())))
      .build();
    server.start();
    PortProber.waitForPortUp(port, 5, TimeUnit.SECONDS);
  }

  String createPage(String title, String body) {
    String html = String.format("<html><head><title>%s</title></head><body>%s</body></html>", title, body);
    try {
      File pageFile = File.createTempFile("page", ".html", tfs);
      Files.write(pageFile.toPath(), html.getBytes());
      return String.format("http://localhost:%s/%s", port, pageFile.getName());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void cleanup() {
    server.stop();
    TemporaryFilesystem.getDefaultTmpFS().deleteTemporaryFiles();
  }
}
