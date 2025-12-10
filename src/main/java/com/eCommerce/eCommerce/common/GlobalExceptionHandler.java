package com.eCommerce.eCommerce.common;

import com.eCommerce.eCommerce.auth.TokenNotValid;
import com.eCommerce.eCommerce.carts.CartNotFoundException;
import com.eCommerce.eCommerce.orders.OrderNotFoundException;
import com.eCommerce.eCommerce.payments.PaymentException;
import com.eCommerce.eCommerce.products.ProductNotFoundException;
import com.eCommerce.eCommerce.users.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException exception){
        var errors = new HashMap<String,String>();
        exception.getBindingResult().getFieldErrors().forEach(error ->{
                    errors.put(error.getField(),error.getDefaultMessage());
                }
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error","cart not found")
        );
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error","product not found")
        );
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<Map<String,String>> handlePaymentException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of("error","error in checkout session")
        );
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUsertNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error","user not found")
        );
    }


    @ExceptionHandler(InvalidOldPasswordException.class)
    public ResponseEntity<Map<String,String>> handleInvalidOldPassword(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error","wrong password")
        );
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExists(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error","email already exist")
        );
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCategoryNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error","category not found")
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleEmailNotFound(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error","email not found")
        );
    }
    @ExceptionHandler(TokenNotValid.class)
    public ResponseEntity<Map<String,String>> handleTokenNotValid(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error","token not valid")
        );
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleOrderNotFoundException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error","Order not valid")
        );
    }
}
