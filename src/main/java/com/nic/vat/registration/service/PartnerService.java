package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerPartner;
import com.nic.vat.registration.model.dto.PartnerRequest;
import com.nic.vat.registration.repository.DealerPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PartnerService {

    @Autowired
    private DealerPartnerRepository partnerRepository;

    public boolean savePartner(PartnerRequest request) {
        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());

            // Auto-generate next sno for the given ackNo
            BigDecimal maxSno = partnerRepository.findMaxSnoByAckNo(ackNo);
            BigDecimal newSno = maxSno.add(BigDecimal.ONE);

            DealerPartner partner = new DealerPartner();
            partner.setAckNo(ackNo);
            partner.setSno(newSno);
            partner.setPartnerType(request.getPartnerType());
            partner.setName(request.getName());
            partner.setFathersName(request.getFathersName());

            // Optional fields
            if (request.getDateOfBirth() != null)
                partner.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
            partner.setDesignation(request.getDesignation());
            partner.setQualification(request.getQualification());
            partner.setPan(request.getPan());
            partner.setAddressLine1(request.getPresentAddress());
            partner.setArea(request.getArea());
            partner.setVillage(request.getVillage());
            partner.setPermanentAddress1(request.getPermanentAddress());

            // Contact
            if (request.getContact() != null) {
                partner.setTelephone(request.getContact().getTelephone());
                partner.setFax(request.getContact().getFax());
                partner.setEmail(request.getContact().getEmail());
            }

            // Interest %
            partner.setInterestPercent(request.getInterestPercent());

            // Partnership Dates
            if (request.getPartnershipDates() != null) {
                if (request.getPartnershipDates().getEntryDate() != null)
                    partner.setEntryDate(LocalDate.parse(request.getPartnershipDates().getEntryDate()));
                if (request.getPartnershipDates().getExitDate() != null)
                    partner.setExitDate(LocalDate.parse(request.getPartnershipDates().getExitDate()));
            }

            // Electoral details
            if (request.getElectoralDetails() != null) {
                partner.setVoterId(request.getElectoralDetails().getVoterId());
                partner.setResidentialCertNo(request.getElectoralDetails().getResidentialCertNo());
            }

            partnerRepository.save(partner);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<DealerPartner> getPartnersByAckNo(String ackNo) {
        return partnerRepository.findByAckNo(new BigDecimal(ackNo));
    }
}


