package ru.stqa.yasen.testenv;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class Page {

  private final File pageFile;
  private int port;

  Page(File tfs, int port) {
    this.port = port;
    try {
      this.pageFile = File.createTempFile("page", ".html", tfs);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public File file() {
    return pageFile;
  }

  public String url() {
    return String.format("http://localhost:%s/%s", port, pageFile.getName());
  }
}
