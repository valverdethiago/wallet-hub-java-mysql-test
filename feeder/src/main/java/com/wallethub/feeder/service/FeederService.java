package com.wallethub.feeder.service;

import com.wallethub.feeder.model.HttpAccess;
import com.wallethub.feeder.repository.HttpAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Service
public class FeederService {

    private final HttpAccessRepository httpAccessRepository;

    private static final Logger logger = LoggerFactory.getLogger(FeederService.class);

    public FeederService(HttpAccessRepository httpAccessRepository) {
        this.httpAccessRepository = httpAccessRepository;
    }

    public void processFiles(List<String> filePaths) {
        if(filePaths == null || filePaths.isEmpty()) {
            return;
        }
        for(String filePath : filePaths) {
            logger.info("Processing file {}", filePath);
            File file = new File(filePath);
            if(!file.exists()) {
                logger.error("File {} can't be found", filePath);
            }
            this.processFile(file);
        }
    }

    private void processFile(File file) {
        FileInputStream fis = null;
        Scanner scanner = null;
        try {
            fis = new FileInputStream(file);
            scanner = new Scanner(fis, Charset.defaultCharset().name());
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                HttpAccess entity = this.parseLine(line);
                this.triggerHttpAccessInsert(entity);
                logger.info("Processing line {}", line);
            }

        }
        catch (FileNotFoundException fnfe) {
            logger.error("File {} can't be found", file.getAbsolutePath());
        }
    }

    @Async
    public CompletableFuture<HttpAccess> triggerHttpAccessInsert(HttpAccess httpAccess) {
        logger.info("Persisting httpAccess {}" , httpAccess);
        return CompletableFuture.completedFuture(this.httpAccessRepository.save(httpAccess));
    }

    public HttpAccess parseLine(String line) {
        // 2017-01-01 00:00:11.763|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"
        String[] args = line.split("\\|");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
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
