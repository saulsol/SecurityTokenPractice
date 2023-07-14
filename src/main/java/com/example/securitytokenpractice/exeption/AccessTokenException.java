package com.example.securitytokenpractice.exeption;

import com.google.gson.Gson;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class AccessTokenException extends RuntimeException {

    TOKEN_ERROR tokenError;

    public enum TOKEN_ERROR {
        UNACCEPT(401, "Token is null or too short"),
        BADTYPE(401, "Token type Bearer"),
        MALFORM(403, "Malformed Token"),
        BADSIGN(403, "BadSignature Token"),
        EXPIRED(403, "Expired Token")
        ;

        private int status;
        private String msg;

        TOKEN_ERROR(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

    }

    public AccessTokenException(TOKEN_ERROR error){
        super(error.name());
        this.tokenError = error;
    }

    public void sendResponseError(HttpServletResponse response){

        response.setStatus(tokenError.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String responseStr = gson.toJson(Map.of("msg", tokenError.getMsg(), "time", new Date()));

        try {
            response.getWriter().println(responseStr);

        }catch (IOException ioException){
            throw new RuntimeException(ioException);
        }


    }






}
