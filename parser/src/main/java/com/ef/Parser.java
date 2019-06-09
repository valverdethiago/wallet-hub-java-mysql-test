package com.ef;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class Parser {

	@Value("${executor.core-pool-size}")
	private Integer corePoolSize;
	@Value("${executor.max-pool-size}")
	private Integer maxPoolSize;
	@Value("${executor.queue-capacity}")
	private Integer queueCapacity;
	@Value("${executor.thread-name-prefix}")
	private String threadNamePrefix;

	public static void main(String[] args) {
		SpringApplication.run(Parser.class, args);
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(this.corePoolSize);
		executor.setMaxPoolSize(this.maxPoolSize);
		executor.setQueueCapacity(this.queueCapacity);
		executor.setThreadNamePrefix(this.threadNamePrefix);
		executor.initialize();
		return executor;
	}

}
