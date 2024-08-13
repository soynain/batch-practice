package com.batchexample.main.Processors;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import com.batchexample.main.Models.Accounts;

public class AccountsProcessor implements ItemProcessor<Accounts,Accounts>{

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

        return processedAccount;
    }
    
}
