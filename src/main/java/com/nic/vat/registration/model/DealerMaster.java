package com.nic.vat.registration.model;

import jakarta.persistence.*;   // JPA annotations
import lombok.Data;            // Auto-generates getters, setters

@Entity                        // Tells Spring this is a database table
@Table(name = "web_trn_dlr_mst", schema = "tvat")
@Data                         // Lombok generates getters/setters/etc.
public class DealerMaster {

    @Id
    @Column(name = "ack_no")      // Maps to SQL column 'ack_no'
    private Long ackNo;

    @Column(name = "appl_name_s") // Maps to 'appl_name_s'
    private String applicantName;

    @Column(name = "mobile")      // Maps to 'mobile'
    private Long mobile;

    @Column(name = "email")       // Maps to 'email'
    private String email;

    @Column(name = "pan")         // Maps to 'pan'
    private String pan;

    @Column(name = "dt_birth")    // Maps to 'dt_birth'
    private java.sql.Date dateOfBirth;

}
