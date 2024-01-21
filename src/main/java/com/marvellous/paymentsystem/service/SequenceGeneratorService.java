package com.marvellous.paymentsystem.service;

import com.marvellous.paymentsystem.models.SequenceGenerator;
import com.marvellous.paymentsystem.models.enums.SequenceName;
import com.marvellous.paymentsystem.repository.SequenceGeneratorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SequenceGeneratorService {

    SequenceGeneratorRepository sequenceGeneratorRepository;

    SequenceGeneratorService(SequenceGeneratorRepository sequenceGeneratorRepository) {
        this.sequenceGeneratorRepository = sequenceGeneratorRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    private Long getNextSequence(SequenceName sequenceName) {
        SequenceGenerator sequenceGenerator = sequenceGeneratorRepository.findById(sequenceName.getValue())
                .orElseGet(() -> {
                    SequenceGenerator newSequenceGenerator = SequenceGenerator.builder()
                                                                            .id(sequenceName.getValue())
                                                                            .sequence(1L)
                                                                            .build();
                    return sequenceGeneratorRepository.save(newSequenceGenerator);
                });

        Long currentSequence = sequenceGenerator.getSequence();
        sequenceGenerator.setSequence(currentSequence + 1L);
        sequenceGeneratorRepository.save(sequenceGenerator);
        return currentSequence;
    }

    public String getNextReceipt() {
        SequenceName sequenceName = SequenceName.RECEIPT;
        return String.format("%s%s", sequenceName.getValue(), getNextSequence(sequenceName));
    }
}
