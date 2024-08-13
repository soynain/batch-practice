package com.batchexample.main.Configurations;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.batchexample.main.Models.Accounts;
import com.batchexample.main.Processors.AccountsProcessor;

@Configuration
public class BatchConfiguration {
    @Bean
    public JsonItemReader<Accounts> jsonItemReader() {
    return new JsonItemReaderBuilder<Accounts>()
        .jsonObjectReader(new JacksonJsonObjectReader<>(Accounts.class))
        .resource(new ClassPathResource("JSON_MOCK_EXAMPLE.json"))
        .name("AccountsJsonItemReader")
        .build();
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
    public Job importUserJob(JobRepository jobRepository,Step step1, JobCompletionNotificationListener listener) {
    return new JobBuilder("importUserJob", jobRepository)
        .listener(listener)
        .start(step1)
        .build();
    }

@Bean
public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
          FlatFileItemReader<Person> reader, PersonItemProcessor processor, JdbcBatchItemWriter<Person> writer) {
  return new StepBuilder("step1", jobRepository)
    .<Person, Person> chunk(3, transactionManager)
    .reader(reader)
    .processor(processor)
    .writer(writer)
    .build();
}
    
}
