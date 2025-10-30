package md.donesk.trendshop.controller;

import md.donesk.trendshop.exception.*;
import org.hibernate.sql.ast.tree.cte.CteColumn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Product Not Found",
                ex.getMessage()) ;
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Category Not Found",
                ex.getMessage()) ;
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFoundException(OrderNotFoundException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Order Not Found",
                ex.getMessage()) ;

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Inventory Not Found",
                ex.getMessage()) ;
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Customer Not Found",
                ex.getMessage()) ;
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSubCategoryNotFoundException(SubCategoryNotFoundException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "SubCategory Not Found",
                ex.getMessage()) ;
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                "Validation failed for one or more fields.") ;

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        Map<String, Object> response = buildErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Database Constraint Violation",
                "A record with the provided SKU already exists. Please use a unique SKU.") ;

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    private Map<String, Object> buildErrorResponse(int status, Object error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("error", error);
        response.put("message", message);
        return response;
    }
}
