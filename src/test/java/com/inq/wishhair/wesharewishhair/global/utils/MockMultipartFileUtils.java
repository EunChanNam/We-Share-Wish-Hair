package com.inq.wishhair.wesharewishhair.global.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class MockMultipartFileUtils {
    private static final String FILE_PATH = "src/test/resources/images/";

    public static List<MultipartFile> generateFiles(List<String> filenames) throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        for (String filename : filenames) {
            files.add(createMultipartFile(filename));
        }
        return files;
    }

    private static MultipartFile createMultipartFile(String originalFilename) throws IOException {
        try (FileInputStream stream = new FileInputStream(FILE_PATH + originalFilename)) {
            return new MockMultipartFile("files", originalFilename, "image/bmp", stream);
        }
    }
}
