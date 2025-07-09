package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerAddress;
import com.nic.vat.registration.model.dto.AdditionalBusinessPlaceRequest;
import com.nic.vat.registration.repository.DealerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DealerAddressService {

    @Autowired
    private DealerAddressRepository repository;

    // ✅ Static district name → code lookup
    private static final Map<String, BigDecimal> DISTRICT_CODES = Map.ofEntries(
            Map.entry("West Tripura", new BigDecimal("1")),
            Map.entry("Sepahijala", new BigDecimal("2")),
            Map.entry("Khowai", new BigDecimal("3")),
            Map.entry("Gomati", new BigDecimal("4")),
            Map.entry("South Tripura", new BigDecimal("5")),
            Map.entry("Dhalai", new BigDecimal("6")),
            Map.entry("North Tripura", new BigDecimal("7")),
            Map.entry("Unakoti", new BigDecimal("8"))
            // 🔁 Add more districts if needed
    );

    public void saveAdditionalBusinessPlace(AdditionalBusinessPlaceRequest req) {
        DealerAddress address = new DealerAddress();

        address.setAckNo(new BigDecimal(req.getApplicationNumber()));
        address.setTinNo(BigDecimal.ZERO); // placeholder; update if actual TIN is available
        address.setSno(new BigDecimal(System.currentTimeMillis() % 10000)); // simple unique serial

        address.setName(req.getName());
        address.setAddr1(req.getStreet());
        address.setAddr2(req.getArea());
        address.setPlace(req.getCity());

        // ✅ Validate and map district name to numeric code
        if (!DISTRICT_CODES.containsKey(req.getDistrict())) {
            throw new IllegalArgumentException("Invalid district. Please use one of the following: " +
                    String.join(", ", DISTRICT_CODES.keySet()));
        }
        address.setDistCd(DISTRICT_CODES.get(req.getDistrict()));

        address.setStCode(req.getState());
        address.setPin(new BigDecimal(req.getPinCode()));
        address.setPhone(req.getTelephone());

        address.setBranchType(req.getBranchType());
        address.setBranchLoc("Within State".equalsIgnoreCase(req.getLocation()) ? "W" : "O");
        address.setRegCstAct(req.getRegistrationNo());
        address.setRegStateAct(req.getUnderStateAct());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        address.setEdrAmendDate(LocalDate.parse(req.getEdrDate(), formatter));

        repository.save(address);
    }

    public List<DealerAddress> getBusinessPlacesByAckNo(String ackNo) {
        return repository.findByAckNo(new BigDecimal(ackNo));
    }
}
