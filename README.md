# Tax_NIC

# VAT Registration Backend – Internship Project (NIC Tripura)

This backend application is built using **Spring Boot** and **PostgreSQL** to support the **VAT registration process** for NIC Tripura. The system is modularized to support multiple parts of registration (Part A, B, etc.), with the current focus on implementing **Part A**.

---

## ✅ Status Summary

| Component             | Status   | Notes                                      |
|----------------------|----------|--------------------------------------------|
| Database connection  | ✅ Done   | Connected to `vatdb`, schema `tvat`        |
| DTO Design           | ✅ Done   | Request-mapped DTOs created for Part A     |
| Entity Mapping       | ✅ Done   | Partial mapping of `web_trn_dlr_mst`       |
| API Implementation   | ✅ Done   | `POST /registration/part-a` tested & live |
| Application Number   | ✅ Mocked | Dummy number returned                      |
| Password Generation  | ✅ Mocked | Dummy password returned                    |
| Data Persistence     | ✅ Done   | Saved to `web_trn_dlr_mst`                 |
| Error Handling       | 🟡 Basic | Currently logs server errors only          |
| Validation           | 🔴 TODO   | Field-level validation not yet implemented |

---

## 🛠️ Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.3
- **ORM**: Hibernate (JPA)
- **Database**: PostgreSQL 14 (local)
- **Build Tool**: Maven
- **Dependencies**:
    - `lombok` – Auto-generate getters/setters
    - `spring-boot-starter-data-jpa`
    - `spring-boot-starter-web`
    - `spring-boot-starter-validation`
    - `postgresql` driver

---

## 📡 API: Part A Registration

**Endpoint**:  
`POST /registration/part-a`

**Purpose**:  
Saves applicant’s basic info from the Part A registration form, generates application number & password, and persists it to the DB.

**Request Payload**:

```json
{
  "typeOfRegistration": "VOLUNTARY",
  "office": "HeadQuarter",
  "businessConstitution": "Proprietary",
  "applicantName": "Ravi Kumar",
  "fathersName": "Mahesh Kumar",
  "dateOfBirth": "1990-05-15",
  "gender": "M",
  "tradingName": "Ravi Electronics",
  "pan": "FRTRT5766K",
  "address": {
    "roomNo": "ROOM 1",
    "area": "AREA 1",
    "village": "VILLAGE 1",
    "district": "Dhalai",
    "pinCode": "799001",
    "occupancyStatus": "Owned"
  },
  "contact": {
    "telephone": "0361-2251234",
    "fax": "",
    "email": "ravi@example.com",
    "mobile": "9999999999"
  }
  
}
```

**Response**:
```json

{
  "success": true,
  "applicationNumber": "202400318526",
  "password": "XyZ@1234",
  "message": "Registration successful. Check your email and mobile for credentials."
}

```


🧱 Internal Architecture
1. DTOs

Stored inside: com.nic.vat.registration.model.dto

    PartARequest.java

    AddressDTO.java

    ContactDTO.java

These classes deserialize the JSON input and separate input structure from DB entity.
2. Entity

Stored inside: com.nic.vat.registration.model
DealerMaster.java

Mapped to the tvat.web_trn_dlr_mst table. Only relevant fields from the Part A schema are currently mapped to avoid complexity and ensure atomic testing.

@Entity
@Table(name = "web_trn_dlr_mst", schema = "tvat")
public class DealerMaster {
    @Id
    @Column(name = "ack_no")
    private BigDecimal ackNo;

    @Column(name = "trad_name")
    private String tradName;

    @Column(name = "fath_name")
    private String fathName;

    // ... and other mapped fields based on Part A form
}

3. Repository

Stored inside: com.nic.vat.registration.repository

@Repository
public interface DealerMasterRepository extends JpaRepository<DealerMaster, BigDecimal> {
}

4. Service

Stored inside: com.nic.vat.registration.service
Method: savePartA(PartARequest request)

    Converts DTO → Entity

    Generates mock ackNo and password

    Saves to DB via DealerMasterRepository

    Returns credentials to frontend

🔄 Example Flow

    Frontend sends POST request to /registration/part-a with JSON body.

    DTO maps the body using Jackson.

    Service generates mock values.

    Hibernate persists DealerMaster entity to the PostgreSQL table.

    JSON response with status 200 is sent back.

🧪 Testing Environment
Component	Setup
OS	Ubuntu 22.04
DB GUI	psql CLI & IntelliJ Data Tab
Testing Tool	Postman / IntelliJ HTTP Client
Debugging	Logs + SQL Console
🗂 Table Schema

Table: tvat.web_trn_dlr_mst
Primary Key: ack_no
Fields used in Part A:

    trad_name

    fath_name

    dt_birth

    sex

    pan

    mobile

    email

    perm_addr, perm_place, perm_pin, perm_dist_cd

    resi_add1, resi_place, resi_pin, resi_dist_cd

    etc.

🧹 Dev Notes

    ✅ Manually created the tvat.web_trn_dlr_mst table in PostgreSQL

    ✅ Synced schema and column names with DB

    ✅ Fixed column-mapping issues (trad_name, sex, ack_date)

    ⚠️ All data types aligned with DB spec (BigDecimal, LocalDate, etc.)

🔜 Next Goals

    ⬜ Add field-level validations using javax.validation annotations

    ⬜ Send actual email and SMS using external services

    ⬜ Add exception handling and custom error responses

    ⬜ Develop Part B form API

    ⬜ Add Swagger documentation

    ⬜ Write JUnit + Mockito test cases