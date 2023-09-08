package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return Paths.get(this.fileDir, filename).toString();
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        var storeFileResult = new ArrayList<UploadFile>();
        for (var multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(this.storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        var originalFilename = multipartFile.getOriginalFilename();
        var storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(this.getFullPath(storeFilename)));
        return new UploadFile(originalFilename, storeFilename);
    }

    private String createStoreFilename(String originalFilename) {
        var ext = extractExtIncludingDot(originalFilename);
        var uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }

    private String extractExtIncludingDot(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        var pos = originalFilename.lastIndexOf(".");
        if (pos == -1) {
            return "";
        }
        return originalFilename.substring(pos);
    }
}
