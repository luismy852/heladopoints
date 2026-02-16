package com.luisdev.heladopoints.service;

import com.luisdev.heladopoints.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PointService {
    private final OcrService ocrService;
    private final UserService usuarioService;
    private final ScanService scanService;
    private final ReceiptService receiptService;

    public PointService(OcrService ocrService, UserService usuarioService, ScanService scanService, ReceiptService receiptService) {
        this.ocrService = ocrService;
        this.usuarioService = usuarioService;
        this.scanService = scanService;
        this.receiptService = receiptService;
    }

    public boolean checkPoints(String email, MultipartFile file) throws IOException {


        User user = usuarioService.findByEmail(email);
        receiptService.processUpload(file, user.getId());

        try {
            boolean check = ocrService.detectText(file);
            if (check){
                usuarioService.addPoints(user);
                scanService.registerTry(user, true, "Add point success");
                return true;
            }
        } catch (Exception e) {
            scanService.registerTry(user, false, "Error: " + e.getMessage());
            throw e;
        }


        return false;
    }

}
