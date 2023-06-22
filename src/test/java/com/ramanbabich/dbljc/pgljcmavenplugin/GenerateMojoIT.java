/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ramanbabich.dbljc.pgljcmavenplugin;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.shared.verifier.VerificationException;
import org.apache.maven.shared.verifier.Verifier;
import org.junit.jupiter.api.Test;

/**
 * @author Raman Babich
 */
class GenerateMojoIT {

  @Test
  void shouldGenerate() throws Exception {
    String testPomDir = "/src/test/resources/integration-tests/should-generate";
    Verifier verifier = buildVerifier(testPomDir);
    verifier.addCliArguments("clean", "compile");

    verifier.execute();

    verifier.verifyFilePresent(
        "target/classes/com/ramanbabich/dbljc/pgljcmavenpluginit/jooq/tables/Data.class");

    //verifier = buildVerifier(testPomDir);
    //verifier.addCliArguments("clean");
    //verifier.execute();
  }

  private Verifier buildVerifier(String pomDir) {
    try {
      String userDir = System.getProperty("user.dir");
      Verifier verifier = new Verifier(userDir + pomDir);
      String logFileName = "target/log.txt";
      Path logFilePath = Paths.get(verifier.getBasedir(), logFileName);
      Files.createDirectories(logFilePath.getParent());
      try {
        Files.createFile(logFilePath);
      } catch (FileAlreadyExistsException ex) {
        // ignore
      }
      verifier.setLogFileName(logFileName);
      return verifier;
    } catch (VerificationException | IOException ex) {
      throw new RuntimeException(ex);
    }
  }

}
