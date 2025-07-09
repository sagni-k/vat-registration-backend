package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerAddress;
import com.nic.vat.registration.model.dto.AdditionalBusinessPlaceRequest;
import com.nic.vat.registration.repository.DealerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.math.BigDecimal;

@Service
public class DealerAddressService {

    @Autowired
    private DealerAddressRepository repository;

    public void saveAdditionalBusinessPlace(AdditionalBusinessPlaceRequest req) {
        DealerAddress address = new DealerAddress();

        address.setAckNo(new BigDecimal(req.getApplicationNumber()));
        address.setTinNo(BigDecimal.ZERO); // placeholder; update if you can fetch actual TIN
        address.setSno(new BigDecimal(System.currentTimeMillis() % 10000)); // simple unique serial

        address.setName(req.getName());
        address.setAddr1(req.getStreet());
        address.setAddr2(req.getArea());
        address.setPlace(req.getCity());

        address.setDistCd(new BigDecimal(req.getDistrict())); // requires code, not name
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
