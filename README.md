# Sales Management System  

A modern microservices-based application for managing sales, products, and customers. This project leverages a scalable architecture and state-of-the-art technologies to deliver a secure and user-friendly experience.

---

## Features  

### **Frontend (Store-Frontend)**  
- Manage sales, products, and customers.  
- View sales statistics.  
- Generate client invoices.  
- Add new administrators.  

### **Backend Services**  
1. **Authentication Service**:  
   - Secure authentication and authorization using Spring Security and JWT.  
2. **StoreBackend Service**:  
   - CRUD operations for products and clients.  
3. **SalesManagement Service**:  
   - Manage sales and invoices.  
   - Generate sales statistics.  

### **API Gateway**  
- Acts as a central entry point for all requests.  
- Routes requests to appropriate backend services.  
- Provides security and service discovery via Spring Cloud Gateway and Consul.

---

## Architecture  

The system follows a **microservices architecture** with the following components:  
![SalesCloud drawio](https://github.com/user-attachments/assets/5639e5c2-647e-4236-95f8-85d107d9c085)
### **Technologies Used**  
- Frontend: Angular
- Backend: Spring Boot, Spring Security
- Authentication: JWT (JSON Web Tokens)
- Gateway: Spring Cloud Gateway
- Service Discovery: Consul
- Database: Relational Database MySQL
- Build Tools: Maven
### **Screens**  
![Capture d'écran 2024-12-05 002016](https://github.com/user-attachments/assets/1d6d18dc-792b-4048-ab79-3262ee18a828)
![Capture d'écran 2024-12-05 001550](https://github.com/user-attachments/assets/5f20cace-e05e-4dd5-a8df-10f3896c536b)
