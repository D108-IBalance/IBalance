package com.ssafy.ibalance.common.util;

import com.ssafy.ibalance.child.exception.NotImageException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageStoreUtil {

    private final List<String> allowedExtentionList = List.of("image/jpg", "image/jpeg", "image/png", "image/gif");

    public void checkImage(String fileName, String extension) {
        if(fileName.lastIndexOf(".") == -1 && !allowedExtentionList.contains(extension.toLowerCase())) {
            throw new NotImageException("이미지 형식의 파일이 아닙니다.");
        }
    }
}
