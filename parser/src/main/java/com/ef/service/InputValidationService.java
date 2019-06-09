package com.ef.service;

import com.ef.model.Duration;
import com.ef.model.InputParametersDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class InputValidationService {

    private static final String DATE_PATTERN = "yyyy-MM-dd.HH:mm:ss[.SSS]";

    public InputParametersDto validateAndBuild(ApplicationArguments args) {
        InputParametersDto dto = new InputParametersDto();
        dto.setAccessLog(validateAndExtractAccessLogPath(args));
        dto.setDuration(validateAndExtractDuration(args));
        dto.setStartDate(validateAndExtractStartDate(args));
        dto.setThreshold(validateAndExtractThreshold(args));
        return dto;
    }

    protected File validateAndExtractAccessLogPath(ApplicationArguments args) {
        if(!args.containsOption("accesslog")) {
            throw new IllegalArgumentException("Accesslog argument is mandatory");
        }
        String accesslogArg = args.getOptionValues("accesslog").get(0).trim();
        File file = new File(accesslogArg);
        return extractFileFromArgument(file);
    }

    protected File extractFileFromArgument(File file) {
        if(file.exists()) {
            return file;
        }
        throw new IllegalArgumentException(String.format("File %s could not be found.", file.getAbsolutePath()));
    }

    protected Integer validateAndExtractThreshold(ApplicationArguments args) {
        if(!args.containsOption("threshold")) {
            throw new IllegalArgumentException("Threshold argument is mandatory");
        }
        String thresholdArg = args.getOptionValues("threshold").get(0).trim();
        return Integer.valueOf(thresholdArg);
    }

    protected LocalDateTime validateAndExtractStartDate(ApplicationArguments args) {
        if(!args.containsOption("startDate")) {
            throw new IllegalArgumentException("StartDate argument is mandatory");
        }
        String startDateArg = args.getOptionValues("startDate").get(0).trim();
        try {
            return LocalDateTime.parse(startDateArg, DateTimeFormatter.ofPattern(DATE_PATTERN));
        }
        catch (DateTimeParseException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("StartDate must be provided using the pattern "+DATE_PATTERN);
        }
    }

    protected Duration validateAndExtractDuration(ApplicationArguments args) {
        if(!args.containsOption("duration")) {
            throw new IllegalArgumentException("Duration argument is mandatory");
        }
        String durationArg = args.getOptionValues("duration").get(0).trim();
        Optional<Duration> durationOptional = 
                Arrays.stream(Duration.values()).filter(
                        duration -> duration.name().toLowerCase().equals(durationArg))
                        .findFirst();
        return durationOptional.orElseThrow(() -> 
                new IllegalArgumentException("Invalid parameter for duration, which should be 'hourly' or 'daily'"));
    }

}
