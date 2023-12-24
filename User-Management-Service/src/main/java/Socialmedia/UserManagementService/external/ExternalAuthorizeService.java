package Socialmedia.UserManagementService.external;
import Socialmedia.UserManagementService.dto.AuthorizationResponse;
import Socialmedia.UserManagementService.dto.AuthorizeRequest;
import Socialmedia.UserManagementService.error.CustomFeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "User-Auth-Service", url = "http://localhost:8081",configuration = {CustomFeignClientConfiguration.class})
public interface ExternalAuthorizeService {

    @RequestMapping(method = RequestMethod.POST,value="/api/auth/authorize",produces = MediaType.APPLICATION_JSON_VALUE)
    AuthorizationResponse authorizeUser(@RequestBody AuthorizeRequest request);
}
