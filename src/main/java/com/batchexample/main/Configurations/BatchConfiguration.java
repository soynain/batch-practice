package com.batchexample.main.Configurations;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.batchexample.main.JobListeners.AccountProcessListener;
import com.batchexample.main.Models.Accounts;
import com.batchexample.main.Processors.AccountsProcessor;

@Configuration

@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Bean(destroyMethod="")
    public JsonItemReader<Accounts> jsonItemReader() {
        try {
            return new JsonItemReaderBuilder<Accounts>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Accounts.class))
                .resource(new ClassPathResource("JSON_MOCK_EXAMPLE.json"))
                .name("AccountsJsonItemReader")
                .build();
        } catch (Exception e) {
            logger.error("Failed to create JsonItemReader", e);
            throw new RuntimeException("Error creating JsonItemReader", e); // Rethrow as a runtime exception
        }
    }

    @Bean
    public AccountsProcessor processor() {
        return new AccountsProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Accounts> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Accounts>()
                .sql("INSERT INTO batch_table_migrate (sej_uuid,description,code,parent_sad_uuid) VALUES (:sej_uuid, :description,:code,:parent_sad_uuid)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, AccountProcessListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
            JsonItemReader<Accounts> reader, AccountsProcessor processor, JdbcBatchItemWriter<Accounts> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Accounts, Accounts>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
