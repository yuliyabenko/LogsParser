package com.epam.rd.november2017;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogsReader {

    private ArrayList<LogEntity> logs = new ArrayList<>();

    private static final Pattern logEntryPattern =
            Pattern.compile("([\\d]{4}\\-[\\d]{2}\\-[\\d]{2}\\s[\\d]{2}:[\\d]{2}:[\\d]{2}\\.[\\d]{3})" +
                    "\\s([A-Z]{4})\\s\\s\\s[\\w]+:\\s'([A-Z]{3})'\\s[\\w]+:\\s'([\\w]+\\s*[\\w]*)'" +
                    "\\s[\\w]+\\s[\\w]+:\\s([\\d]+)[\\w]{2}");

    public LogsReader(String path) {
        fillListOfLogs(path);
    }

    public ArrayList<LogEntity> getLogs() {
        return logs;
    }

    private void fillListOfLogs(String path) {
        List<String> list = readFromFile(path);
        for (String line : list) {
            Matcher matcher = logEntryPattern.matcher(line);
            if (!matcher.matches()) {
                System.err.println("Bad log entry or problem with RE!");
                return;
            }
            String strDate = matcher.group(1);
            //Между датой и временем заменить пробел на Т
            String dateForParse = strDate.replace(" ", "T");
            LocalDateTime date = LocalDateTime.parse(dateForParse);
            LogEntity log = new LogEntity(date, matcher.group(2), matcher.group(3), matcher.group(4), Integer.parseInt(matcher.group(5)));
            logs.add(log);

        }
    }

    private List<String> readFromFile(String path) {
        File file = new File(path);
        String fileName = file.getName();
        List<String> list = new ArrayList<>();
        try (FileReader fw = new FileReader(file);
             BufferedReader bw = new BufferedReader(fw)) {
            Scanner sc = new Scanner(bw);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}