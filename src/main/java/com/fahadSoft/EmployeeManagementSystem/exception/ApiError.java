package com.fahadSoft.EmployeeManagementSystem.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    private int statusCode;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String debugMessage;
    private List<String> errors;

    // লুম্বক থাকা সত্ত্বেও এই একটি কাস্টম কনস্ট্রাক্টর রাখা ভালো,
    // কারণ এটি HttpStatus থেকে statusCode-এর মানটি স্বয়ংক্রিয়ভাবে সেট করে নেয়।
    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.statusCode = status.value(); // HttpStatus থেকে int ভ্যালু (যেমন: 404) নিচ্ছে
        this.message = message;
        this.debugMessage = (ex != null) ? ex.getLocalizedMessage() : null;
    }
}
