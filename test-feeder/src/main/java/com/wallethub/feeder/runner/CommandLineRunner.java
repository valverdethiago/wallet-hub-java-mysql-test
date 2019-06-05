package com.wallethub.feeder.runner;

import com.wallethub.feeder.service.FeederService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("!test")
public class CommandLineRunner implements ApplicationRunner {

	@Autowired
	private FeederService feederService;

	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

	@Override
	public void run(ApplicationArguments args) {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
		logger.info("OptionNames: {}", args.getOptionNames());
		if(!args.containsOption("file")) {
			logger.error("Argument file is mandatory. Please execute using --file=<PATH>");
			throw new IllegalArgumentException("Argument file is mandatory");
		}
		feederService.processFiles(args.getOptionValues("file"));

	}
}
