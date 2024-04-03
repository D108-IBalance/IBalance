package com.ssafy.ibalance.common;

import com.ssafy.ibalance.common.type.CustomMultipartFile;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class MockMultipartTestUtil extends TestBase {

    public MockMultipartFile 이미지_간단생성() {

        try {
            String fileFullName = "image.png";
            File file = new File(System.getProperty("java.io.tmpdir"), fileFullName);

            BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
            ImageIO.write(image, "png", file);

            return createMockMultipartFile(file, fileFullName, "image", "image/png");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MockMultipartFile 텍스트_파일_생성(String filename, String inputContent, String fieldName) {
        try {
            String fileFullName = filename + ".txt";
            File file = new File(System.getProperty("java.io.tmpdir"), fileFullName);

            Path path = file.toPath();
            Files.write(path, inputContent.getBytes());
            return createMockMultipartFile(file, fileFullName, fieldName, "text/txt");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private MockMultipartFile createMockMultipartFile(File file, String fileName, String fieldName, String fileType) throws IOException {
        DiskFileItem fileItem = new DiskFileItem("file", "application/octet-stream", false, file.getName(), (int) file.length(), file.getParentFile());
        fileItem.getOutputStream().write(Files.readAllBytes(file.toPath()));

        MultipartFile multipartFile = new CustomMultipartFile(fileItem.get(), fileItem.getName());

        return new MockMultipartFile(fieldName, fieldName, fileType, multipartFile.getBytes());
    }
}
