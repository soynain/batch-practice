package com.batchexample.main.Processors;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.batchexample.main.Models.Accounts;

public class AccountsProcessor implements ItemProcessor<Accounts,Accounts>{

    public static final Logger logger = LoggerFactory.getLogger(AccountsProcessor.class);

    @Override
    public Accounts process(Accounts accountToProcess) throws Exception {
        /*Como pre proceso y solo para el ejemplito, haremos que si, un registro
         * no trae parent_sad_uuid, lo genere. Aqui no seguimos una logica de negocios,
         * es solo saber sobre el lifecycle basico de la herramienta.
         */

        Accounts processedAccount = new Accounts(
            accountToProcess.sej_uuid(),
            accountToProcess.description(),
            accountToProcess.code(),
            accountToProcess.parent_sad_uuid() != null ? accountToProcess.parent_sad_uuid() : UUID.randomUUID().toString()
        );

        logger.info("PROCESANDO REGISTRO "+processedAccount.toString());

        return processedAccount;
    }
    
}
