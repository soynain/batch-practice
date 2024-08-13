package com.batchexample.main.JobListeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class AccountProcessListener implements JobExecutionListener{

    public static Logger logger = LoggerFactory.getLogger(AccountProcessListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // TODO Auto-generated method stub
        logger.info("SI SE EJECUTA");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("!!! JOB FINISHED! Time to verify the results");
        }else{
            logger.info("HUBO UN ERROR EN LA COMPLETACIÓN DEL PROCESO");
        }
    }

}
