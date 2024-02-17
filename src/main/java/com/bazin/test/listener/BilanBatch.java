package com.bazin.test.listener;

import com.bazin.test.config.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BilanBatch implements StepExecutionListener {

    @Autowired
    private JobInfo jobInfo;

    @Override
    public ExitStatus afterStep(@NotNull StepExecution stepExecution) {

        log.info("Nombre de lignes lues : {}",stepExecution.getReadCount());
        log.info("Nombre de clients crées : {}", jobInfo.getCountClients());
        log.info("Nombre de comptes crées : {}", jobInfo.getCountComptes());
        log.info("Nombre de lignes en erreurs : {}", jobInfo.getCountError());

        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
