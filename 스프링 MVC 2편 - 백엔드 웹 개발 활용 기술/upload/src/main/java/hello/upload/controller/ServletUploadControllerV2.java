package hello.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request);

        var itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName);

        var parts = request.getParts();
        log.info("parts = {}", parts);

        for (var part : parts) {
            log.info("===== PART =====");
            log.info("name = {}", part.getName());
            var headerNames = part.getHeaderNames();
            for (var headerName : headerNames) {
                log.info("header {} : {}", headerName, part.getHeaders(headerName));
            }

            // 편의 메서드
            // Content-Disposition; filename
            log.info("submittedFileName = {}", part.getSubmittedFileName());
            // part body size
            log.info("size = {}", part.getSize());

            // 데이터 읽기
            var inputStream = part.getInputStream();
            var body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body = {}", body);

            // 파일 저장
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                var fullPath = Paths.get(this.fileDir, part.getSubmittedFileName()).toString();
                log.info("파일 저장 fullPath={}", fullPath);
                part.write(fullPath);
            }
        }

        return "upload-form";
    }
}
