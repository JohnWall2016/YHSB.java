package cn.yhsb.cjb.application;

import cn.yhsb.base.util.CommandWithHelp;
import cn.yhsb.base.util.DateTime;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import static cn.yhsb.base.util.IO.*;

import com.google.common.base.Strings;

@Command(description = "居保参保审核")
public class Audit extends CommandWithHelp {
    public static void main(String[] args) {
        new CommandLine(new Audit()).execute(args);
    }

    @Parameters(index = "0", description = "审核开始时间, 例如: 20200301")
    String startDate;

    @Parameters(index = "1", defaultValue = "", description = "审核结束时间, 例如: 20200310")
    String endDate;

    @Override
    public void run() {
        var start = Strings.isNullOrEmpty(startDate) ? "" : DateTime.toDashedDate(startDate);
        var end = Strings.isNullOrEmpty(endDate) ? "" : DateTime.toDashedDate(endDate);
        var span = "";
        if (!start.equals("")) {
            span += start;
            if (!end.equals("")) {
                span += "_" + end;
            }
        }
        println(span);


    }
}