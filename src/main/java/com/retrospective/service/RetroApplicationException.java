package com.retrospective.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RetroApplicationException extends RuntimeException {

    private final String errorCode;

    public RetroApplicationException( final String pCode, final String msg) {
        super(msg);
        this.errorCode = pCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code: ").append(errorCode).append(" ").append(super.toString());
        return sb.toString();
    }


}
