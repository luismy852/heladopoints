package com.luisdev.heladopoints.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.luisdev.heladopoints.exception.InvalidReceiptException;
import com.luisdev.heladopoints.exception.OcrProccessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OcrService {

    public Boolean detectText(MultipartFile filePath) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        String[] words = {"CONO", "PALETA", "HELADO", "ICE CREAM"};
        String[] billWords = {"IVA", "TOTAL", "FACTURA"};
        byte[] bytes = filePath.getBytes();
        ByteString imgBytes = ByteString.copyFrom(bytes);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();

        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    throw new OcrProccessingException("Google Vision error: " + res.getError().getMessage());
                }

                List<EntityAnnotation> annotations = res.getTextAnnotationsList();
                if (annotations.isEmpty()) {
                    throw new InvalidReceiptException("No text could be detected. Please ensure the photo is clear and contains a receipt.");
                }
                String text = res.getTextAnnotationsList().get(0).getDescription().toUpperCase();

                boolean hasProduct = Arrays.stream(words).anyMatch(text::contains);
                boolean isBill = Arrays.stream(billWords).allMatch(text::contains);
                if (hasProduct && isBill){
                    return true;
                } else if (isBill) {
                    throw new InvalidReceiptException("No valid ice cream products were found on this receipt.");
                } else {
                    throw  new InvalidReceiptException("the image does not appear to be a valid official receipt.");
                }
            }
        }catch (IOException e){
            throw new OcrProccessingException("Could not connect to Google server " + e.getMessage());
        }
        return false;
    }


}