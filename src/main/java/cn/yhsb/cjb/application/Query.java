package cn.yhsb.cjb.application;

import cn.yhsb.base.util.CommandWithHelp;
import cn.yhsb.cjb.Session;
import cn.yhsb.cjb.request.GrinfoRequest;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(description = "城居保信息查询程序", subcommands = { Query.Grinfo.class, Query.Jfxx.class })
public class Query extends CommandWithHelp {
    public static void main(String[] args) {
        new CommandLine(new Query()).execute(args);
    }

    @Command(name = "grinfo", description = "个人综合查询")
    public static class Grinfo extends CommandWithHelp {
        @Parameters(description = "身份证号码")
        String[] idcards;

        @Override
        public void run() {
            if (idcards != null) {
                Session.user002(session -> {
                    for (var idcard : idcards) {
                        session.sendService(new GrinfoRequest(idcard));
                        var result = session.getResult(GrinfoRequest.Grinfo.class);
                        if (result.empty()) {
                            System.out.println(idcard + " 未在我区参保");
                        } else {
                            var info = result.getDatas().get(0);
                            System.out.println(String.format("%s %s %s %s %s", info.getIdcard(), info.getName(),
                                    info.getJbztCN(), info.getDwmc(), info.getCzmc()));
                        }
                    }
                });
            }
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
