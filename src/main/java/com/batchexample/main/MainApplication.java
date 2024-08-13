package com.batchexample.main;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.batchexample.main.Configurations.BatchConfiguration;

@SpringBootApplication
public class MainApplication {


	@Autowired
    private final JobLauncher jobLauncher;
	@Autowired
    private final Job importUserJob;

    public MainApplication(JobLauncher jobLauncher, Job importUserJob) {
        this.jobLauncher = jobLauncher;
        this.importUserJob = importUserJob;
    }
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	    @Bean
    public CommandLineRunner runJob() {
        return args -> {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
            try {
                jobLauncher.run(importUserJob, jobParameters);
                System.out.println("Job started successfully");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to start job");
            }
        };
    }
}
