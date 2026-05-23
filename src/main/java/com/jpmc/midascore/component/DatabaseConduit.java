package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    // public DatabaseConduit(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    public DatabaseConduit(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public UserRecord findUser(long id) {
        return userRepository.findById(id);
    }

    public void save(TransactionRecord record) {
        transactionRecordRepository.save(record);
    }

}
