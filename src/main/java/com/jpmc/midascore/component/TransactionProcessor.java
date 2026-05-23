package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TransactionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);
    private final DatabaseConduit databaseConduit;

    public TransactionProcessor(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @Transactional
    public void process(Transaction transaction) {
        UserRecord sender = databaseConduit.findUser(transaction.getSenderId());
        UserRecord recipient = databaseConduit.findUser(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            return;
        }

        if (sender.getBalance() < transaction.getAmount()) {
            return;
        }

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());

        // sender.getId() == 5L || recipient.getId() == 5L
        // if (sender.getId() == 5L || recipient.getId() == 5L) {
        //     logger.info("Transaction processed: {} sender: {} recipient: {} amount: {}", transaction, sender, recipient, transaction.getAmount());
        // }

        databaseConduit.save(sender);
        databaseConduit.save(recipient);

        databaseConduit.save(new TransactionRecord(sender, recipient, transaction.getAmount()));
    }
}
