package com.ssafy.ibalance.common.type;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomMultipartFile implements MultipartFile {

    byte[] input;
    String name;
    String contentType;

    public CustomMultipartFile(byte[] input, String name) {
        this.input = input;
        this.name = name;
        this.contentType = "image/png";
    }

    public CustomMultipartFile(byte[] input, String name, String contentType) {
        this.input = input;
        this.name = name;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return this.name;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() {
        return input;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        FileUtils.writeByteArrayToFile(destination, input);
    }
}