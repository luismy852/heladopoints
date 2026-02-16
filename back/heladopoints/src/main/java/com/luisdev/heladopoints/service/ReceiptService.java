package com.luisdev.heladopoints.service;

import com.luisdev.heladopoints.exception.DuplicateReceiptException;
import com.luisdev.heladopoints.model.Receipt;
import com.luisdev.heladopoints.repository.ReceiptRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ReceiptService {

    private final ReceiptRepository repository;

    public ReceiptService(ReceiptRepository repository) {
        this.repository = repository;
    }

    public void processUpload(MultipartFile file, Long userId) throws IOException {
        String newHash = generateHash(file);
        boolean exists = repository.existsByFileHash(newHash);
        if (exists){
            throw new DuplicateReceiptException("This file has already been uploaded.");
        }

        Receipt receipt = new Receipt(newHash, userId);
        repository.save(receipt);
    }

    private String generateHash(MultipartFile file) throws IOException {
        return DigestUtils.md5DigestAsHex(file.getBytes());
    }
}
