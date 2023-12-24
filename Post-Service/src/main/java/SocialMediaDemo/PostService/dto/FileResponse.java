package SocialMediaDemo.PostService.dto;

import lombok.Data;

@Data
public class FileResponse {

    private String text;

    private String filename;

    private String fileType;

    private String fileSize;

    private byte[] file;
}
