package com.ef.model;

import java.io.File;
import java.time.LocalDateTime;

public class InputParametersDto {

    private File accessLog;
    private LocalDateTime startDate;
    private Duration duration;
    private Integer threshold;

    public File getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(File accessLog) {
        this.accessLog = accessLog;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
