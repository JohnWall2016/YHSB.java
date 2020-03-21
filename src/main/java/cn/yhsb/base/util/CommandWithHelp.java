package cn.yhsb.base.util;

import picocli.CommandLine.Command;

@Command(mixinStandardHelpOptions = true)
public abstract class CommandWithHelp implements Runnable {

}