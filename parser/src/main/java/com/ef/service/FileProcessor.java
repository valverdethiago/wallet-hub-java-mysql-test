package com.ef.service;

import com.ef.model.HttpAccess;
import com.ef.repository.BlockedIpRepository;
import com.ef.repository.HttpAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
public class FileProcessor {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private final HttpAccessRepository httpAccessRepository;
    private final BlockedIpRepository blockedIpRepository;

    private static final Logger logger = LoggerFactory.getLogger(FileProcessor.class);

    public FileProcessor(HttpAccessRepository httpAccessRepository, BlockedIpRepository blockedIpRepository) {
        this.httpAccessRepository = httpAccessRepository;
        this.blockedIpRepository = blockedIpRepository;
    }

    public void processFile(File file) {
        FileInputStream fis = null;
        Scanner scanner = null;
        try {
            fis = new FileInputStream(file);
            this.cleanUpDatabase();
            scanner = new Scanner(fis, Charset.defaultCharset().name());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                HttpAccess entity = this.parseLine(line);
                this.httpAccessRepository.save(entity);
                logger.info("Processing line {}", line);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("File {} can't be found", file.getAbsolutePath());
        }
    }

    protected void cleanUpDatabase() {
        this.httpAccessRepository.deleteAll();
        this.blockedIpRepository.deleteAll();
    }

    protected HttpAccess parseLine(String line) {
        // 2017-01-01 00:00:11.763|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"
        String[] args = line.split("\\|");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDateTime timestamp = LocalDateTime.parse(args[0], formatter);
        String ipAddress = args[1];
        String protocolInfo = stripDoubleQuotesFromBeginningAndEnd(args[2]);
        String httpStatusCode = args[3];
        String requestDetail = stripDoubleQuotesFromBeginningAndEnd(args[4]);
        return new HttpAccess(timestamp, ipAddress, protocolInfo, httpStatusCode, requestDetail);
    }

    private String stripDoubleQuotesFromBeginningAndEnd(String text) {
        return text.replaceAll("^\"|\"$", "");
    }
}
