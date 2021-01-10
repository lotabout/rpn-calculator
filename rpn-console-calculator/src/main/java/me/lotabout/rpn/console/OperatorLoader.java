package me.lotabout.rpn.console;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperatorLoader extends URLClassLoader {
  public OperatorLoader(URL[] urls) {
    super(urls);
  }

  public OperatorLoader() {
    super(new URL[0]);
  }

  public synchronized void addJarOrDir(String jarName) throws MalformedURLException {
    File file = Paths.get(jarName).toFile();
    if (file.isDirectory()) {
      for (File jar : Objects.requireNonNull(file.listFiles())) {
        addJarOrDir(jar.getAbsolutePath());
      }
    } else {
      addJar(jarName);
    }
  }

  public synchronized void addJar(String jarName) throws MalformedURLException {
    File file = Paths.get(jarName).toFile();
    if (file.isFile() && jarName.endsWith(".jar")) {
      log.info("add jar " + jarName);
      addURL(Paths.get(jarName).toUri().toURL());
    }
  }
}
