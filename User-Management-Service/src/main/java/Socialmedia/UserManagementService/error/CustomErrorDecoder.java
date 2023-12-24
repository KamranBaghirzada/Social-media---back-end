package Socialmedia.UserManagementService.error;

import Socialmedia.UserManagementService.exception.NotRecognizedException;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.security.SignatureException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        if (status == 401) {
                return new SignatureException("Problem in Jwt Validation .");
        }
        return FeignException.errorStatus(methodKey, response);
    }
}
