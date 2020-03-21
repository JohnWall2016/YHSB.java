package cn.yhsb.cjb.application;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(description = "城居保信息查询程序", subcommands = {Query.Grinfo.class, Query.Jfxx.class},
        mixinStandardHelpOptions = true)
public class Query implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new Query()).execute(args);
    }

    @Command(name = "grinfo", description = "个人信息查询", mixinStandardHelpOptions = true)
    public static class Grinfo implements Runnable {
        @Override
        public void run() {
            System.out.println("grinfo");
        }
    }

    @Command(name = "jfxx", description = "缴费信息查询", mixinStandardHelpOptions = true)
    public static class Jfxx implements Runnable {
        @Override
        public void run() {
            System.out.println("jfxx");
        }
    }

    @Override
    public void run() {
        System.out.println("query");
    }
}
