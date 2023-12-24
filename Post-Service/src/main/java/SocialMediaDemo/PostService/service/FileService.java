package SocialMediaDemo.PostService.service;

import SocialMediaDemo.PostService.dto.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface FileService {

     Optional<FileResponse> getFileById(String fileId) throws IOException;
     Page<FileResponse> getAllFiles(Pageable pageable) throws IOException;
}
