package com.example.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "¡Hola Mundo! Spring Boot con H2 y Docker funcionando correctamente.";
    }

    @GetMapping("/api/status")
    public Status status() {
        return new Status("OK", "Aplicación Spring Boot está corriendo", System.currentTimeMillis());
    }

    public static class Status {
        private String status;
        private String message;
        private long timestamp;

        public Status(String status, String message, long timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
