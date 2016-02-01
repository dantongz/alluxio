/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.shell.command;

import java.io.IOException;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import tachyon.TachyonURI;
import tachyon.client.file.FileSystem;
import tachyon.conf.TachyonConf;

/**
 * Changes the permission of a file or directory specified by args.
 */
@ThreadSafe
public final class ChmodCommand extends AbstractAclCommand {

  /**
   * Creates a new instance of {@link ChmodCommand}.
   *
   * @param conf a Tachyon configuration
   * @param fs a Tachyon file system handle
   */
  public ChmodCommand(TachyonConf conf, FileSystem fs) {
    super(conf, fs);
  }

  @Override
  public String getCommandName() {
    return "chmod";
  }

  @Override
  protected int getNumOfArgs() {
    return 2;
  }

  @Override
  protected Options getOptions() {
    Options opts = new Options();
    // Add R option for recursively.
    Option recursive = Option.builder("R")
        .required(false)
        .hasArg(false)
        .desc("recusively")
        .build();

    opts.addOption(recursive);
    return opts;
  }

  @Override
  public void run(CommandLine cl) throws IOException {
    String[] args = cl.getArgs();
    String modeStr = args[0];
    TachyonURI path = new TachyonURI(args[1]);
    chmod(path, modeStr, cl.hasOption("R"));
  }

  @Override
  public String getUsage() {
    return "chmod -R <mode> <path>";
  }

  @Override
  public String getDescription() {
    return "Changes the permission of a file or directory specified by args. "
        + " Specify -R to change the permission recursively.";
  }
}
