package com.luisdev.heladopoints.repository;

import com.luisdev.heladopoints.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    boolean existsByFileHash(String hash);

}
