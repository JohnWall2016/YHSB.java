package cn.yhsb.cjb.application;

import cn.yhsb.base.util.CommandWithHelp;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(description = "城居保信息查询程序", subcommands = {Query.Grinfo.class, Query.Jfxx.class})
public class Query extends CommandWithHelp {
    public static void main(String[] args) {
        new CommandLine(new Query()).execute(args);
    }

    @Command(name = "grinfo", description = "个人信息查询")
    public static class Grinfo extends CommandWithHelp {
        @Override
        public void run() {
            System.out.println("grinfo");
        }
    }

    @Command(name = "jfxx", description = "缴费信息查询")
    public static class Jfxx extends CommandWithHelp {
        @Override
        public void run() {
            System.out.println("jfxx");
        }
    }

    @Override
    public void run() {
        // System.out.println("query");
        CommandLine.usage(new Query(), System.out);
    }
}
