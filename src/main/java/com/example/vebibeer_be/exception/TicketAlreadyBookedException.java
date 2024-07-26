package com.example.vebibeer_be.exception;

public class TicketAlreadyBookedException extends RuntimeException {
    public TicketAlreadyBookedException(String message) {
        super(message);
    }
}
