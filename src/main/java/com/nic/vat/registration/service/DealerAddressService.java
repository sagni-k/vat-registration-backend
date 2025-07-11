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
    );

    private static final Map<BigDecimal, String> DISTRICT_NAMES = new HashMap<>();

    static {
        DISTRICT_CODES.forEach((k, v) -> DISTRICT_NAMES.put(v, k));
    }

    public void saveAdditionalBusinessPlace(AdditionalBusinessPlaceRequest req) {
        DealerAddress address = new DealerAddress();

        address.setAckNo(new BigDecimal(req.getApplicationNumber()));
        address.setTinNo(BigDecimal.ZERO);
        address.setSno(new BigDecimal(System.currentTimeMillis() % 10000));

        address.setName(req.getName());
        address.setAddr1(req.getStreet());
        address.setAddr2(req.getArea());
        address.setPlace(req.getCity());

        // Validate and map district
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

        if (req.getEdrDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            address.setEdrAmendDate(LocalDate.parse(req.getEdrDate(), formatter));
        }

        address.setApplicantName(req.getApplicantName()); // ✅ Store it

        repository.save(address);
    }

    public List<Map<String, Object>> getBusinessPlacesByAckNo(String ackNo) {
        List<DealerAddress> records = repository.findByAckNo(new BigDecimal(ackNo));
        List<Map<String, Object>> result = new ArrayList<>();

        for (DealerAddress a : records) {
            Map<String, Object> map = new HashMap<>();
            map.put("applicationNumber", a.getAckNo().toPlainString());
            map.put("applicantName", a.getApplicantName());
            map.put("location", "W".equalsIgnoreCase(a.getBranchLoc()) ? "Within State" : "Outside State");
            map.put("registrationNo", a.getRegCstAct());
            map.put("underStateAct", a.getRegStateAct());
            map.put("branchType", a.getBranchType());
            map.put("name", a.getName());
            map.put("street", a.getAddr1());
            map.put("area", a.getAddr2());
            map.put("city", a.getPlace());
            map.put("district", DISTRICT_NAMES.getOrDefault(a.getDistCd(), ""));
            map.put("state", a.getStCode());
            map.put("pinCode", a.getPin() != null ? a.getPin().toPlainString() : null);
            map.put("telephone", a.getPhone());
            map.put("edrDate", a.getEdrAmendDate() != null ? a.getEdrAmendDate().toString() : null);

            result.add(map);
        }

        return result;
    }
}
