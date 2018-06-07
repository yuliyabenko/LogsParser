package com.epam.rd.november2017.ui;

import com.epam.rd.november2017.LogsStatistic;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.time.LocalDateTime;

public class App {

    private enum Action {
        PRINT,
        LOAD,
        SHOW
    }

    @Option(name = "-n", usage = "topNumber")
    private int topNumber;

    @Option(name = "-from", usage = "startDate")
    private String startDate;

    @Option(name = "-range", usage = "range")
    private int range;

    @Option(name = "-f", usage = "fileName")
    private String fileName;

    @Argument(usage = "action", required = true, metaVar = "action")
    private Action action;

    private void doMain(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        if (args.length == 0) {
            System.out.println("My example with songs catalog. Usage: ");
            parser.printUsage(System.out);
            return;
        } else {
            parser.parseArgument(args);
        }
        LogsStatistic logsStatistic = new LogsStatistic();
        String correctDate;
        LocalDateTime date;
        switch (action) {
            case PRINT:
                correctDate = startDate.replace(" ","T");
                date = LocalDateTime.parse(correctDate);
                System.out.println(logsStatistic.longestOperationsByModules(topNumber, date, range));
                break;
            case LOAD:
                correctDate = startDate.replace(" ","T");
                date = LocalDateTime.parse(correctDate);
                logsStatistic.writeStatisticToFile(logsStatistic.longestOperationsByModules(topNumber, date, range), "logs/" + fileName);
                break;
            case SHOW:
                System.out.println(logsStatistic.readStatisticFromFile("logs/" + fileName));
                break;
            default:
                throw new AssertionError("No way!");
        }
    }

    public static void main(String[] args) throws CmdLineException {
        new App().doMain(args);
    }
}
