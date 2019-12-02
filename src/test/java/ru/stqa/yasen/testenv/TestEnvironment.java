package ru.stqa.yasen.testenv;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.net.PortProber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class TestEnvironment {

  private File tfs;
  private int port;
  private Undertow server;

  public TestEnvironment() {
    tfs = TemporaryFilesystem.getDefaultTmpFS().createTempDir("yasen", "tests");
    port = PortProber.findFreePort();
    server = Undertow.builder()
      .addHttpListener(port, "localhost")
      .setHandler(new ResourceHandler(new PathResourceManager(tfs.toPath())))
      .build();
    server.start();
    PortProber.waitForPortUp(port, 5, TimeUnit.SECONDS);
  }

  public Page allocatePage() {
    return new Page(tfs, port);
  }

  public String createPage(String body) {
    return createPage(allocatePage(), "", "", body);
  }

  public String createPage(Page page, String body) {
    return createPage(page, "", "", body);
  }

  public String createPage(String title, String body) {
    return createPage(allocatePage(), title, "", body);
  }

  public String createPage(Page page, String title, String body) {
    return createPage(page, title, "", body);
  }

  public String createPage(String title, String script, String body) {
    return createPage(allocatePage(), title, script, body);
  }

  public String createPage(Page page, String title, String script, String body) {
    String html = String.format("<html><head><title>%s</title>"
      + "<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" "
      + "integrity=\"sha256-pasqAKBDmFT4eHoN2ndd6lN370kFiGUFyTiUHWhU7k8=\" crossorigin=\"anonymous\"></script>"
      + "<script>%s</script>"
      + "</head><body>%s</body></html>",
      title, script, body);
    try {
      Files.write(page.file().toPath(), html.getBytes());
      return page.url();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void cleanup() {
    server.stop();
    TemporaryFilesystem.getDefaultTmpFS().deleteTemporaryFiles();
  }
}
