package com.nic.vat.registration.service;

import com.nic.vat.registration.model.DealerPartner;
import com.nic.vat.registration.model.dto.PartnerRequest;
import com.nic.vat.registration.model.dto.PartnerResponse;
import com.nic.vat.registration.repository.DealerPartnerRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PartnerService {

    @Autowired
    private DealerPartnerRepository partnerRepository;

    public boolean savePartner(PartnerRequest request, MultipartFile idProof, MultipartFile addrProof) {
        try {
            BigDecimal ackNo = new BigDecimal(request.getApplicationNumber());

            List<DealerPartner> existingPartners = partnerRepository.findByAckNo(ackNo);
            DealerPartner existing = existingPartners.stream()
                    .filter(p -> request.getPan().equalsIgnoreCase(p.getPan()))
                    .findFirst()
                    .orElse(null);

            DealerPartner partner = (existing != null) ? existing : new DealerPartner();

            if (existing == null) {
                BigDecimal maxSno = partnerRepository.findMaxSnoByAckNo(ackNo);
                BigDecimal newSno = maxSno.add(BigDecimal.ONE);
                partner.setAckNo(ackNo);
                partner.setSno(newSno);
            }

            partner.setPartnerType(request.getPartnerType());
            partner.setName(request.getName());
            partner.setFathersName(request.getFathersName());
            partner.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
            partner.setDesignation(request.getDesignation());
            partner.setQualification(request.getQualification());
            partner.setPan(request.getPan());
            partner.setAddressLine1(request.getPresentAddress());
            partner.setArea(request.getArea());
            partner.setVillage(request.getVillage());
            partner.setPermanentAddress1(request.getPermanentAddress());

            if (request.getContact() != null) {
                partner.setTelephone(request.getContact().getTelephone());
                partner.setFax(request.getContact().getFax());
                partner.setEmail(request.getContact().getEmail());
            }

            partner.setInterestPercent(request.getInterestPercent());

            if (request.getPartnershipDates() != null) {
                if (request.getPartnershipDates().getEntryDate() != null)
                    partner.setEntryDate(LocalDate.parse(request.getPartnershipDates().getEntryDate()));
                if (request.getPartnershipDates().getExitDate() != null)
                    partner.setExitDate(LocalDate.parse(request.getPartnershipDates().getExitDate()));
            }

            if (request.getElectoralDetails() != null) {
                partner.setVoterId(request.getElectoralDetails().getVoterId());
                partner.setResidentialCertNo(request.getElectoralDetails().getResidentialCertNo());
            }

            if (idProof != null) {
                partner.setDocIdName(idProof.getOriginalFilename());
                partner.setDocIdType(idProof.getContentType());
                partner.setDocIdSize(idProof.getSize());
                partner.setDocIdFile(idProof.getBytes());
            }

            if (addrProof != null) {
                partner.setDocAddrName(addrProof.getOriginalFilename());
                partner.setDocAddrType(addrProof.getContentType());
                partner.setDocAddrSize(addrProof.getSize());
                partner.setDocAddrFile(addrProof.getBytes());
            }

            partnerRepository.save(partner);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PartnerResponse> getPartnersByAckNo(String ackNo) {
        BigDecimal ack = new BigDecimal(ackNo);
        List<DealerPartner> entities = partnerRepository.findByAckNo(ack);

        return entities.stream().map(partner -> {
            PartnerResponse dto = new PartnerResponse();
            dto.setPartnerType(partner.getPartnerType());
            dto.setName(partner.getName());
            dto.setFathersName(partner.getFathersName());
            dto.setDateOfBirth(partner.getDateOfBirth() != null ? partner.getDateOfBirth().toString() : null);
            dto.setDesignation(partner.getDesignation());
            dto.setQualification(partner.getQualification());
            dto.setPan(partner.getPan());
            dto.setPresentAddress(partner.getAddressLine1());
            dto.setArea(partner.getArea());
            dto.setVillage(partner.getVillage());
            dto.setPermanentAddress(partner.getPermanentAddress1());
            dto.setInterestPercent(partner.getInterestPercent());

            PartnerResponse.ContactDTO contact = new PartnerResponse.ContactDTO();
            contact.setTelephone(partner.getTelephone());
            contact.setFax(partner.getFax());
            contact.setEmail(partner.getEmail());
            dto.setContact(contact);

            PartnerResponse.PartnershipDatesDTO dates = new PartnerResponse.PartnershipDatesDTO();
            dates.setEntryDate(partner.getEntryDate() != null ? partner.getEntryDate().toString() : null);
            dates.setExitDate(partner.getExitDate() != null ? partner.getExitDate().toString() : null);
            dto.setPartnershipDates(dates);

            PartnerResponse.ElectoralDetailsDTO electoral = new PartnerResponse.ElectoralDetailsDTO();
            electoral.setVoterId(partner.getVoterId());
            electoral.setResidentialCertNo(partner.getResidentialCertNo());
            dto.setElectoralDetails(electoral);

            PartnerResponse.UploadedDocument uploaded = new PartnerResponse.UploadedDocument();

            PartnerResponse.DocumentMeta idProof = new PartnerResponse.DocumentMeta();
            idProof.setName(partner.getDocIdName());
            idProof.setType(partner.getDocIdType());
            idProof.setSize(partner.getDocIdSize() != null ? partner.getDocIdSize() : 0L);
            uploaded.setIdProof(idProof);

            PartnerResponse.DocumentMeta addrProof = new PartnerResponse.DocumentMeta();
            addrProof.setName(partner.getDocAddrName());
            addrProof.setType(partner.getDocAddrType());
            addrProof.setSize(partner.getDocAddrSize() != null ? partner.getDocAddrSize() : 0L);
            uploaded.setAddressProof(addrProof);

            dto.setUploadedDocument(uploaded);
            return dto;
        }).toList();
    }

    public void writeFileToResponse(String ackNo, String pan, String fileType, HttpServletResponse response) throws IOException {
        BigDecimal ack = new BigDecimal(ackNo);
        DealerPartner partner = partnerRepository.findByAckNo(ack).stream()
                .filter(p -> pan.equalsIgnoreCase(p.getPan()))
                .findFirst()
                .orElse(null);

        if (partner == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] fileBytes;
        String fileName;
        String contentType;

        if (fileType.equals("id")) {
            fileBytes = partner.getDocIdFile();
            fileName = partner.getDocIdName();
            contentType = partner.getDocIdType();
        } else {
            fileBytes = partner.getDocAddrFile();
            fileName = partner.getDocAddrName();
            contentType = partner.getDocAddrType();
        }

        if (fileBytes == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        StreamUtils.copy(fileBytes, response.getOutputStream());
    }
}
