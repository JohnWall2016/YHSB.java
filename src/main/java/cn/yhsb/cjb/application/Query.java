package cn.yhsb.cjb.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import cn.yhsb.base.util.CommandWithHelp;
import cn.yhsb.cjb.Session;
import cn.yhsb.cjb.request.CbxxRequest;
import cn.yhsb.cjb.request.GrinfoRequest;
import cn.yhsb.cjb.request.JfxxRequest.Jfxx;
import cn.yhsb.cjb.service.Result;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(description = "城居保信息查询程序", subcommands = {Query.GrinfoQuery.class, Query.JfxxQuery.class})
public class Query extends CommandWithHelp {
    public static void main(String[] args) {
        new CommandLine(new Query()).execute(args);
    }

    @Command(name = "grinfo", description = "个人综合查询")
    public static class GrinfoQuery extends CommandWithHelp {
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
                            System.out.format("%s %s %s %s %s\n", info.getIdcard(), info.getName(),
                                    info.getJbState(), info.getDwmc(), info.getCzmc());
                        }
                    }
                });
            }
        }
    }

    @Command(name = "jfxx", description = "缴费信息查询")
    public static class JfxxQuery extends CommandWithHelp {
        @Option(names = {"-e", "--export"}, description = "导出信息表")
        boolean export = false;

        @Parameters(description = "身份证号码")
        String idcard;

        /** 缴费记录 */
        static class JfxxRecord {
            /** 年度 */
            Integer year;
            /** 个人缴费 */
            BigDecimal grjf = new BigDecimal(0);
            /** 省级补贴 */
            BigDecimal sjbt = new BigDecimal(0);
            /** 市级补贴 */
            BigDecimal sqbt = new BigDecimal(0);
            /** 县级补贴 */
            BigDecimal xjbt = new BigDecimal(0);
            /** 政府代缴 */
            BigDecimal zfdj = new BigDecimal(0);
            /** 集体补助 */
            BigDecimal jtbz = new BigDecimal(0);

            /** 划拨日期 */
            HashSet<String> hbrq = new HashSet<String>();
            /** 社保机构 */
            HashSet<String> sbjg = new HashSet<String>();

            public JfxxRecord(int year) {
                this.year = year;
            }

            public JfxxRecord() {

            }
        }

        /** 缴费合计记录 */
        static class JfxxTotalRecord extends JfxxRecord {
            /** 合计 */
            BigDecimal total = new BigDecimal(0);
        }

        void getJfxxRecords(Result<Jfxx> jfxx, HashMap<Integer, JfxxRecord> paidRecords,
                HashMap<Integer, JfxxRecord> unpaidRecords) {
            for (var data : jfxx.getDatas()) {
                if (data.year != null) {
                    var records = data.paidOff() ? paidRecords : unpaidRecords;
                    if (!records.containsKey(data.year)) {
                        records.put(data.year, new JfxxRecord(data.year));
                    }
                    var record = records.get(data.year);
                    switch (data.item.getValue()) {
                        case "1":
                            record.grjf = record.grjf.add(data.amount);
                            break;
                        case "3":
                            record.sjbt = record.sjbt.add(data.amount);
                            break;
                        case "4":
                            record.sqbt = record.sqbt.add(data.amount);
                            break;
                        case "5":
                            record.xjbt = record.xjbt.add(data.amount);
                            break;
                        case "6":
                            record.jtbz = record.jtbz.add(data.amount);
                            break;
                        case "11":
                            record.zfdj = record.zfdj.add(data.amount);
                            break;
                        default:
                            System.out.println(
                                    "未知缴费类型" + data.item.getValue() + ", 金额" + data.amount);
                            break;
                    }
                    record.sbjg.add(data.agency != null ? data.agency : "");
                    record.hbrq.add(data.paidOffDay != null ? data.paidOffDay : "");
                }
            }
        }

        List<JfxxRecord> orderAndSum(HashMap<Integer, JfxxRecord> records) {
            var results =
                    records.values().stream().sorted((e1, e2) -> Integer.compare(e1.year, e2.year));
            var total = new JfxxTotalRecord();
            results.forEach(r -> {
                total.grjf = total.grjf.add(r.grjf);
                total.sjbt = total.sjbt.add(r.sjbt);
                total.sqbt = total.sqbt.add(r.sqbt);
                total.xjbt = total.xjbt.add(r.xjbt);
                total.zfdj = total.zfdj.add(r.zfdj);
                total.jtbz = total.jtbz.add(r.jtbz);
            });
            total.total = total.total.add(total.grjf).add(total.sjbt).add(total.sqbt)
                    .add(total.xjbt).add(total.zfdj).add(total.jtbz);
            var list = results.collect(Collectors.toCollection(ArrayList<JfxxRecord>::new));
            list.add(total);
            return list;
        }

        void printInfo(CbxxRequest info)
        {
            /*WriteLine("个人信息:");
            WriteLine(
                $"{info.name} {info.idcard} {info.JBState} " +
                $"{info.JBClass} {info.agency} {info.czName} " +
                $"{info.dealDate}\n"
            );*/
        }

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
