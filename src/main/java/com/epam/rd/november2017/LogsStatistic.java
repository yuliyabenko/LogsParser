package com.epam.rd.november2017;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LogsStatistic {

    private LogsReader logsReader = new LogsReader("logs/info.log");
    private ArrayList<LogEntity> logs = logsReader.getLogs();
    private ArrayList<ArrayList<LogEntity>> modules = new ArrayList<>();

    public StringBuilder longestOperationsByModules(int topNumber, LocalDateTime date, int range) {
        splitLogsByModule();
        sortLogsInModules();
        StringBuilder sb = new StringBuilder();
        LocalDateTime dateEnd = date.plusHours(range);
        sb.append("Top " + topNumber + " operations, starting from \"" + date.toString().replace("T", " ")
                + "\" for " + range + " hours");
        sb.append("\n");
        for (int i = 0; i < modules.size(); i++) {
            ArrayList<LogEntity> module = modules.get(i);
            sb.append(module.get(0).getModule() + " Module: ");
            sb.append("\n");
            int top = topNumber;
            for (int j = 0; j < module.size(); j++) {
                if ((module.get(j).getDate().isAfter(date) || module.get(j).getDate().isEqual(date))
                        && (module.get(j).getDate().isBefore(dateEnd) || module.get(j).getDate().isEqual(dateEnd))) {
                    String newDate = module.get(j).getDate().toString();
                    String correctDate = newDate.replace("T", " ");
                    sb.append("\t" + module.get(j).getOperation() + " " +
                            module.get(j).getDuration() + "ms, finished at " +
                            correctDate);
                    sb.append("\n");
                    top--;
                }
                if (top == 0) {
                    break;
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb;

    }

    private void splitLogsByModule() {
        String tmp;
        for (int i = 0; i < logs.size(); i++) {
            tmp = logs.get(i).getModule();
            ArrayList<LogEntity> logsOfModule = new ArrayList<>();
            logsOfModule.add(logs.get(i));
            for (int j = i + 1; j < logs.size(); j++) {
                if (tmp.equals(logs.get(j).getModule())) {
                    logsOfModule.add(logs.get(j));
                    logs.remove(j);
                    j--;
                }
            }
            modules.add(i, logsOfModule);
        }
    }

    private void sortLogsInModules() {
        for (int i = 0; i < modules.size(); i++) {
            Collections.sort(modules.get(i), Collections.reverseOrder());
        }
    }

    public void writeStatisticToFile(StringBuilder sb, String path) {
        File file = new File(path);
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)) {
            PrintWriter pw = new PrintWriter(bw);
            pw.print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder readStatisticFromFile(String path) {
        StringBuilder sb = new StringBuilder();
        File file = new File(path);
        if (file.isDirectory() || !file.exists()) {
            throw new IllegalArgumentException("Incorrect file name!");
        }
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                sb.append(line);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb;
    }
}
