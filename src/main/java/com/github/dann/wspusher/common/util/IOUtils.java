/*
 * Copyright 2012 Dann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
