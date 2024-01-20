package com.marvellous.paymentsystem.repository;

import com.marvellous.paymentsystem.models.SequenceGenerator;
import com.marvellous.paymentsystem.models.enums.SequenceName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceGeneratorRepository extends JpaRepository<SequenceGenerator, String> {

}
