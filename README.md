# 🌐 Tripura VAT Registration Backend

This is the complete backend system for **Tripura’s upcoming VAT Registration Portal**, built during my internship at **NIC Tripura** (Ministry of Electronics & IT, Govt. of India). The system is intended to power **real-world tax workflows** and will be integrated into the **official state-wide application**.


## 🚀 Project Highlights

- 🔐 **Secure RESTful APIs** using Spring Boot
- 🧾 **Modular multi-step form** registration (Parts A–C, Bank, Documents, Partners, etc.)
- 🔑 **JWT-based authentication** for session management
- 🗂️ **File upload & storage in PostgreSQL** using `@Lob` (e.g., Aadhaar, Electricity Bills)
- 🔄 **Dynamic max-SNO logic** for record insertion
- 🧩 **Clean DTO ↔ Entity mapping** using layered architecture
- 🌐 **Hosted on Render** for production access

---

## 🧱 Tech Stack

| Layer              | Tech Used                          |
|--------------------|------------------------------------|
| **Backend**        | Spring Boot (Java)                 |
| **Database**       | PostgreSQL (NIC’s `tvat` schema)   |
| **Authentication** | JWT (JSON Web Token)               |
| **Deployment**     | Render.com                         |
| **API Style**      | RESTful, stateless                 |

---

## 🧭 API Modules

### 📌 Authentication

- `POST /auth/login` – Verifies app number, password, captcha → returns JWT token

### 🧍 Part-A: Applicant Info

- `POST /registration/part-a` – Registers new user → returns app number & password
- `PUT /registration/part-a` – Updates existing application
- `GET /registration/part-a` – Fetches saved info

### 🧾 Multi-step Form APIs

- `POST /registration/part-b`, `part-c`, `bank-info`, `partner`, `documents`
- Each step saves independently and uses JWT token

### 📁 File Upload & Retrieval

- Uploads stored in DB as binary (`@Lob`) with MIME type & metadata
- Retrieval supports metadata & document streaming via download endpoints

---

## 🧠 Architecture Overview

The system uses a **layered, maintainable backend structure**:

- **DTO Layer** – Accepts well-structured requests from frontend
- **Entity Layer** – Maps to NIC’s official PostgreSQL schema
- **Service Layer** – Handles logic (insertion/update with max-SNO, validation, etc.)
- **Controller Layer** – Defines token-authenticated endpoints using `@RestController`

---

## 🔐 JWT Authentication

- Users receive a token upon login
- Token is sent in headers (`Authorization: Bearer <token>`) for all further steps
- Stateless security — no server-side sessions

---

## 📂 File Upload Logic

- Only valid MIME types (PDF, JPEG, PNG) accepted
- Files like Aadhaar & Electricity Bill stored as byte arrays
- Metadata (name, type, size) is returned in API response
- Documents viewable via `/download/...` endpoints

---

## 💡 Advanced Features

- ✅ Dynamic max-SNO record management (used in Partner module)
- ✅ Dual-mode form support (initial + update, e.g., in Part-A)
- ✅ DTO nesting for clean handling of complex objects (e.g., contact, address)
- ✅ Fully integrated with live frontend for UAT testing

---

## 📦 Deployment

- 🖥️ Backend deployed on: [Render.com](https://render.com)
- 📍 PostgreSQL hosted database (NIC's `tvat` schema)

---

## 👨‍💻 Maintainer

**Sagnik Karmakar**  
Backend Developer Intern, NIC Tripura  
[LinkedIn](https://www.linkedin.com/in/sagnik-karmakar-535235261/) | [GitHub](https://github.com/sagni-k)

---

## 📸 Demo Links

- 🔗 **Live Frontend**: [https://vat-registration-frontend.vercel.app/]
- 📂 **Backend GitHub Repo**: [https://github.com/sagni-k/vat-registration-backend]
- 🎥 **Video Walkthrough**: [Insert YouTube Link]

---

## 📜 License

This project was developed under the guidance of NIC Tripura for government use. All source code and database structures conform to NIC’s internal guidelines and schemas.

