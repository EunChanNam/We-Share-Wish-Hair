package com.inq.wishhair.wesharewishhair.global.utils;

import java.util.UUID;

public abstract class FilenameGenerator {
    public static String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private static String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }

    public static String createUploadLink(String bucketName, String storeUrl) {
        return String.format(
                "https://kr.object.ncloudstorage.com/%s/%s",
                bucketName,
                storeUrl
        );
    }
}
