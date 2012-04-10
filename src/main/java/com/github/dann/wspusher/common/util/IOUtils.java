package com.github.dann.wspusher.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

public final class IOUtils {
  private IOUtils() {}

  public static String toString(final InputStream is) throws IOException {
    return toString(is, Charsets.UTF_8);
  }

  public static String toString(final InputStream is, final Charset cs) throws IOException {
    Closeable closeMe = is;
    try {
      final InputStreamReader isr = new InputStreamReader(is, cs);
      closeMe = isr;
      return CharStreams.toString(isr);
    } finally {
      Closeables.closeQuietly(closeMe);
    }
  }

}
