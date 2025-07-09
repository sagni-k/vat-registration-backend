package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerDocument;
import com.nic.vat.registration.repository.DealerDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DocumentUploadService {

    @Autowired
    private DealerDocumentRepository repo;

    public void saveDocuments(String applicationNumber, MultipartFile... files) throws Exception {
        String[] types = {"ID", "ADDRESS", "PAN", "PHOTO"};
        AtomicInteger docNo = new AtomicInteger(1);

        for (int i = 0; i < types.length; i++) {
            MultipartFile file = files[i];
            if (file != null && !file.isEmpty()) {
                DealerDocument doc = new DealerDocument();
                doc.setTinNo(BigDecimal.ZERO); // or derive from another table
                doc.setAckNo(new BigDecimal(applicationNumber));
                doc.setDocType(types[i]);
                doc.setDocCode(i + 1);
                doc.setDocNo(BigDecimal.valueOf(docNo.getAndIncrement()));
                doc.setDocFile(file.getBytes());
                doc.setDocSize(file.getSize() + " bytes");
                doc.setEnteredBy("system");
                doc.setEnteredDt(LocalDate.now());
                doc.setFilename(file.getOriginalFilename());
                doc.setFiletype("B");

                repo.save(doc);
            }
        }
    }

    public List<Map<String, Object>> getDocumentsByAckNo(String ackNo) {
        return repo.findByAckNo(new BigDecimal(ackNo)).stream()
                .map(doc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("docType", doc.getDocType());
                    map.put("filename", doc.getFilename());
                    map.put("docSize", doc.getDocSize());
                    map.put("uploadedOn", doc.getEnteredDt());
                    return map;
                })
                .collect(Collectors.toList());
    }
}