package cn.yhsb.cjb.application;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cn.yhsb.base.util.CommandWithHelp;
import cn.yhsb.base.util.Excels;
import cn.yhsb.cjb.service.Session;
import cn.yhsb.cjb.request.CbxxRequest;
import cn.yhsb.cjb.request.GrinfoRequest;
import cn.yhsb.cjb.request.JfxxRequest;
import cn.yhsb.cjb.request.CbxxRequest.Cbxx;
import cn.yhsb.cjb.request.JfxxRequest.Jfxx;
import cn.yhsb.cjb.service.Result;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import static cn.yhsb.base.util.IO.*;

@Command(description = "城居保信息查询程序", subcommands = { Query.GrinfoQuery.class,
        Query.JfxxQuery.class })
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
                        var result = session
                                .getResult(GrinfoRequest.Grinfo.class);
                        if (result.empty()) {
                            println(idcard + " 未在我区参保");
                        } else {
                            var info = result.getData().get(0);
                            printf("%s %s %s %s %s\n", info.getIdcard(),
                                    info.getName(), info.getJbState(),
                                    info.getDwmc(), info.getCzmc());
                        }
                    }
                });
            }
        }
    }

    @Command(name = "jfxx", description = "缴费信息查询")
    public static class JfxxQuery extends CommandWithHelp {
        @Option(names = { "-e", "--export" }, description = "导出信息表")
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

        static void getJfxxRecords(Result<Jfxx> jfxx,
                HashMap<Integer, JfxxRecord> paidRecords,
                HashMap<Integer, JfxxRecord> unpaidRecords) {
            for (var data : jfxx.getData()) {
                if (data.year != null) {
                    var records = data.paidOff() ? paidRecords : unpaidRecords;
                    if (!records.containsKey(data.year)) {
                        records.put(data.year, new JfxxRecord(data.year));
                    }
                    var record = records.get(data.year);
                    switch(data.item.getValue()) {
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
                        System.out.println("未知缴费类型" + data.item.getValue()
                                + ", 金额" + data.amount);
                        break;
                    }
                    record.sbjg.add(data.agency != null ? data.agency : "");
                    record.hbrq.add(
                            data.paidOffDay != null ? data.paidOffDay : "");
                }
            }
        }

        static List<JfxxRecord> orderAndSum(
                HashMap<Integer, JfxxRecord> records) {
            var results = records.values().stream()
                    .sorted((e1, e2) -> Integer.compare(e1.year, e2.year));
            var list = new ArrayList<JfxxRecord>();
            var total = new JfxxTotalRecord();
            results.forEach(r -> {
                list.add(r);
                total.grjf = total.grjf.add(r.grjf);
                total.sjbt = total.sjbt.add(r.sjbt);
                total.sqbt = total.sqbt.add(r.sqbt);
                total.xjbt = total.xjbt.add(r.xjbt);
                total.zfdj = total.zfdj.add(r.zfdj);
                total.jtbz = total.jtbz.add(r.jtbz);
            });
            total.total = total.total.add(total.grjf).add(total.sjbt)
                    .add(total.sqbt).add(total.xjbt).add(total.zfdj)
                    .add(total.jtbz);
            list.add(total);
            return list;
        }

        static void printInfo(CbxxRequest.Cbxx info) {
            println("个人信息:");
            printf("%s %s %s %s %s %s %s\n", info.getName(), info.getIdcard(),
                    info.getJbState(), info.getJbKind(), info.getAgency(),
                    info.getCzName(), info.getDealDate());
        }

        static String formatRecord(JfxxRecord r) {
            if (!(r instanceof JfxxTotalRecord)) {
                return String.format("%5s%9s%9s%9s%9s%9s%9s  %s %s", r.year,
                        r.grjf, r.sjbt, r.sqbt, r.xjbt, r.zfdj, r.jtbz,
                        String.join("|", r.sbjg), String.join("|", r.hbrq));
            } else {
                return " 合计"
                        + String.format("%9s%9s%9s%9s%9s%9s", r.grjf, r.sjbt,
                                r.sqbt, r.xjbt, r.zfdj, r.jtbz)
                        + "  总计: " + ((JfxxTotalRecord) r).total;
            }
        }

        static void printJfxxRecords(List<JfxxRecord> records, String message) {
            println(message);

            printf("%2s%3s%6s%5s%5s%5s%5s%5s%7s %s\n", "序号", "年度", "个人缴费",
                    "省级补贴", "市级补贴", "县级补贴", "政府代缴", "集体补助", "社保经办机构", "划拨时间");

            var i = 1;
            for (var r : records) {
                String t = r instanceof JfxxTotalRecord ? ""
                        : String.valueOf(i++);
                printf("%3s %s\n", t, formatRecord(r));
            }
        }

        @Override
        public void run() {
            Cbxx info = null;
            Result<Jfxx> jfxx = null;

            try (var session = Session.autoLoginUser002()) {
                session.sendService(new CbxxRequest(idcard));
                var infoResult = session.getResult(Cbxx.class);
                // println(infoResult.toString());
                if (infoResult.empty() || !infoResult.getData().get(0).valid())
                    return;
                info = infoResult.getData().get(0);

                session.sendService(new JfxxRequest(idcard));
                var jfxxResult = session.getResult(Jfxx.class);
                if (!jfxxResult.empty()
                        && jfxxResult.getData().get(0).year != null)
                    jfxx = jfxxResult;
            }

            if (info == null) {
                println("未查到参保记录");
                return;
            }

            printInfo(info);

            List<JfxxRecord> records = null;
            List<JfxxRecord> unrecords = null;

            if (jfxx == null) {
                println("未查询到缴费信息");
            } else {
                var paidRecords = new HashMap<Integer, JfxxRecord>();
                var unpaidRecords = new HashMap<Integer, JfxxRecord>();
                getJfxxRecords(jfxx, paidRecords, unpaidRecords);
                records = orderAndSum(paidRecords);
                unrecords = orderAndSum(unpaidRecords);
                printJfxxRecords(records, "已拨付缴费历史记录:");
                if (unpaidRecords.size() > 0) {
                    printJfxxRecords(unrecords, "\n未拨付补录入记录:");
                }
            }

            if (export) {
                var dir = "D:\\征缴管理";
                var xlsx = Paths.get(dir, "雨湖区城乡居民基本养老保险缴费查询单模板.xlsx");
                var workbook = Excels.load(xlsx.toString());
                var sheet = workbook.getSheetAt(0);
                sheet.getRow(4).getCell(0).setCellValue(info.getName());
                sheet.getRow(4).getCell(2).setCellValue(info.getIdcard());
                sheet.getRow(4).getCell(4).setCellValue(info.getAgency());
                sheet.getRow(4).getCell(6).setCellValue(info.getCzName());
                sheet.getRow(4).getCell(10).setCellValue(info.getDealDate());

                if (records != null) {
                    int index = 8, copyIndex = index;
                    for (var r : records) {
                        var row = Excels.getOrCopyRowFrom(sheet, index++,
                                copyIndex, true);
                        if (r instanceof JfxxTotalRecord) {
                            row.getCell(0).setCellValue("");
                            row.getCell(1).setCellValue("合计");
                        } else {
                            row.getCell(0).setCellValue(index - copyIndex);
                            row.getCell(1).setCellValue(r.year);
                        }
                        row.getCell(2).setCellValue(r.grjf.toString());
                        row.getCell(3).setCellValue(r.sjbt.toString());
                        row.getCell(4).setCellValue(r.sqbt.toString());
                        row.getCell(5).setCellValue(r.xjbt.toString());
                        row.getCell(6).setCellValue(r.zfdj.toString());
                        row.getCell(7).setCellValue(r.jtbz.toString());
                        if (r instanceof JfxxTotalRecord) {
                            row.getCell(8).setCellValue("总计");
                            row.getCell(10).setCellValue(
                                    ((JfxxTotalRecord) r).total.toString());
                        } else {
                            row.getCell(8)
                                    .setCellValue(String.join("|", r.sbjg));
                            row.getCell(10)
                                    .setCellValue(String.join("|", r.hbrq));
                        }
                    }
                }
                Excels.save(workbook, Paths
                        .get(dir, info.getName() + "缴费查询单.xlsx").toString());
            }
        }
    }

    @Override
    public void run() {
        // System.out.println("query");
        CommandLine.usage(new Query(), System.out);
    }
}
