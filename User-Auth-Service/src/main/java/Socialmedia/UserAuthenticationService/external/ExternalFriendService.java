package Socialmedia.UserAuthenticationService.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Friendship-Service", url = "http://localhost:8086")
public interface ExternalFriendService {

    @RequestMapping(method = RequestMethod.POST,value="/api/friendships/register",produces = MediaType.APPLICATION_JSON_VALUE)
    void sendRegistrationInformationToFriendService(String email);
}
