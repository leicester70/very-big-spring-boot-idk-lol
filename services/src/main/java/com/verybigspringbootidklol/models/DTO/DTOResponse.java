package com.verybigspringbootidklol.models.DTO;

public class DTOResponse {
    private String message;

    public DTOResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}