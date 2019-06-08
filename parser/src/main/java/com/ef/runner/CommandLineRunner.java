package com.ef.runner;

import com.ef.model.InputParametersDto;
import com.ef.service.FileProcessor;
import com.ef.service.HttpAccessService;
import com.ef.service.InputValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
public class CommandLineRunner implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

	private final FileProcessor fileProcessor;
	private final InputValidationService inputValidationService;
	private final HttpAccessService httpAccessService;

	@Autowired
	public CommandLineRunner(FileProcessor fileProcessor, InputValidationService inputValidationService, HttpAccessService httpAccessService) {
		this.fileProcessor = fileProcessor;
		this.inputValidationService = inputValidationService;
		this.httpAccessService = httpAccessService;
	}

	@Override
	public void run(ApplicationArguments args) {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
		logger.info("OptionNames: {}", args.getOptionNames());
		InputParametersDto dto = inputValidationService.validateAndBuild(args);
		fileProcessor.processFile(dto.getAccessLog());
		List<String> ips = httpAccessService.blockIps(dto.getStartDate(), dto.getDuration(), dto.getThreshold());
		ips.stream().forEach(ip -> logger.info("Blocking IP {} ", ip));
	}
}
