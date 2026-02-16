package com.luisdev.heladopoints.repository;

import com.luisdev.heladopoints.model.Scan;
import com.luisdev.heladopoints.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScanRepository extends JpaRepository<Scan, Long> {
    List<Scan> findByUserId(Long id);
}
