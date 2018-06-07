package com.epam.rd.november2017;

import java.time.LocalDateTime;

public class LogEntity implements Comparable {
    private LocalDateTime date;
    private String level;
    private String module;
    private String operation;
    private int duration;

    public LogEntity(LocalDateTime date, String level, String module, String operation, int duration) {
        this.date = date;
        this.level = level;
        this.module = module;
        this.operation = operation;
        this.duration = duration;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(Object o) {
        return duration - ((LogEntity) o).duration;
    }
}
