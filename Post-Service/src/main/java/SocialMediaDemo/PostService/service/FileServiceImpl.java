package SocialMediaDemo.PostService.service;

import SocialMediaDemo.PostService.dto.FileResponse;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations gridFsOperations;

    private final ServiceUtil serviceUtil;

    @Override
    public Optional<FileResponse> getFileById(String fileId) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));

        FileResponse fileResponse = new FileResponse();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            fileResponse.setFilename(gridFSFile.getFilename());

            fileResponse.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

            fileResponse.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

            fileResponse.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
        }
        return Optional.of(fileResponse);
    }

    @Override
    public Page<FileResponse> getAllFiles(Pageable pageable) throws IOException {
        Pageable customPageable = serviceUtil.pageableWithSorting(pageable);
        // Query to fetch all files with pagination and sorting
        Query query = new Query().with(customPageable);


        GridFSFindIterable gridFSFindIterable = gridFsTemplate.find(query);
        List<GridFSFile> gridFSFiles = new ArrayList<>();

        for (GridFSFile gridFSFile : gridFSFindIterable) {
            gridFSFiles.add(gridFSFile);
        }

        List<FileResponse> fileResponses = gridFSFiles.stream()
                .map(gridFSFile -> {
                    FileResponse fileResponse = new FileResponse();

                    fileResponse.setFilename(gridFSFile.getFilename());
                    fileResponse.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
                    fileResponse.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

                    try {
                        fileResponse.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return fileResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(fileResponses, customPageable, gridFSFiles.size());
    }
}
