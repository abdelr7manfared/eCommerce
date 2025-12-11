# E-Commerce API — Spring Boot

##  Description 

This project is a clean and production-ready **E-Commerce REST API** built with **Spring Boot**.  
It includes authentication, user profiles, products, cart, orders, and Stripe checkout.  
The architecture is **feature-based**, making the project easy to maintain and extend.

---

##  Features

###  Authentication (JWT)
- Register & Login  
- Secure protected routes  
- Token-based access

###  Users & Profiles
- User basic information  
- Profile (phone, DOB, bio, loyalty points)  
- Address per user

###  Products & Categories
- Product CRUD  
- Category management  
- Clean product–category relationship

###  Cart
- Add / remove items  
- Update quantities  
- User-specific cart storage

###  Orders + Stripe Checkout
- Create orders  
- Generate Stripe Checkout Session  
- Redirect user to Stripe payment  
- Webhook handling  
- Automatically mark orders as **PAID**

---

##  Project Structure (Feature-Based)

Each feature contains its own controller, service, repository, entity, and DTOs:
```
auth/
users/
products/
carts/
orders/
payments/
EcommerceApplication.java
``` 

This structure keeps the code modular and scalable.

---

## 🔗 API Endpoints (Overview)

### Auth  
- `POST /auth/refresh`  
- `POST /auth/login`

### Users  
- `GET /users`  
- `PUT /users`

### Carts
- `GET /carts`  
- `POST /carts`  
- `DELETE /carts/{cartId}/items/{productId}`

### Orders  
- `GET /orders`  
- `GET /orders/{id}`

  
### Payments  
  
- `POST /checkout`
- `POST /checkout/webhook`

  

## Class diagram
![Class](https://github.com/abdelr7manfared/eCommerce/blob/main/docs/classDigram.JPG)


## API Documentation
Swagger UI is available at:

https://ecommerce-production-e6ae.up.railway.app/swagger-ui.html

## Live API (Production)
**Base URL:**  
`https://ecommerce-production-e6ae.up.railway.app`

You can test the API using Postman or any REST client.
