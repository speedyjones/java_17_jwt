package com.hib.hibenatemysql.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLSyntaxErrorException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController implements ErrorController {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<?> sqlSyntax(SQLSyntaxErrorException error) {
        Map<String, String> map = new HashMap<>();
        map.put("Msg ", error.getLocalizedMessage());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullError(NullPointerException error) {
        Map<String, String> map = new HashMap<>();
        map.put("Msg ", error.getLocalizedMessage());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleJwtError(String error) throws ParseException {
        Map<String, String> map = new HashMap<>();
        log.info("msg " + error);

        String dateTime = error.substring(15, 35).strip();
        Instant instant = Instant.parse(dateTime);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.error("date " + zonedDateTime.format(sdf));

        map.put("Msg :", "Token Expired At " + zonedDateTime.format(sdf));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<?> forbidden(HttpClientErrorException.Forbidden error) {
        Map<String, String> map = new HashMap<>();
        map.put("Msg ", error.getLocalizedMessage());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException error) {
        Map<String, String> map = new HashMap<>();
        map.put("Msg ", error.getLocalizedMessage());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
