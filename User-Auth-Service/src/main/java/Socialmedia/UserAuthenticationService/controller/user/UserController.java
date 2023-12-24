package Socialmedia.UserAuthenticationService.controller.user;

import Socialmedia.UserAuthenticationService.dto.DetailsResponse;
import Socialmedia.UserAuthenticationService.dto.UserResponse;
import Socialmedia.UserAuthenticationService.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import Socialmedia.UserAuthenticationService.service.user.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @GetMapping("/get-details")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<DetailsResponse> getDetails(@RequestHeader(name = "Authorization") String authorizationHeader) {
        return ResponseEntity.ok(userService.getDetails(authorizationHeader));
    }

    @PostMapping("/upload-photo")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> addProfilePhoto(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.addProfilePhoto(file));
    }

    @GetMapping("/get-profile-photo")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Resource> addProfilePhoto(@RequestParam String imageURL) throws Exception {
        Resource resource = userService.getProfilePhoto(imageURL);
        MediaType mediaType = determineMediaType(resource.getFilename());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentLength(resource.contentLength());
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete-photo/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteProfilePhoto(@PathVariable("userId") Integer userId) throws IOException {
        userService.deleteProfilePhoto(userId);
        return new ResponseEntity<>("Profile photo has been deleted. ", HttpStatus.NO_CONTENT);
    }

    private MediaType determineMediaType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
