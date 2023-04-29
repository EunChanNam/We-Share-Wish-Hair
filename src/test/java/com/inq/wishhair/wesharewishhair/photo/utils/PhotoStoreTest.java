package com.inq.wishhair.wesharewishhair.photo.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.inq.wishhair.wesharewishhair.global.base.InfraTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class PhotoStoreTest extends InfraTest {

    private static final String BUKET = "buket";

    @Mock
    private AmazonS3Client amazon;
    private PhotoStore photoStore;

    private final List<String> filenames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        photoStore = new PhotoStore(amazon, BUKET);
        for (int i = 1; i <= 3; i++) {
            filenames.add("hello" + i + ".png");
        }
    }

    @Nested
    @DisplayName("이미지 저장 테스트")
    class uploadFiles {
        @Test
        @DisplayName("이미지를 네이버 클라우드에 저장한다")
        void success() throws IOException {
            //given
            List<MultipartFile> files = MockMultipartFileUtils.generateFiles(filenames);

            PutObjectResult putObjectResult = new PutObjectResult();
            given(amazon.putObject(any(PutObjectRequest.class))).willReturn(putObjectResult);

            String storeUrl = createStoreFilename("hello1.png");
            URL mockUrl = new URL(createUploadLink(storeUrl));
            given(amazon.getUrl(any(), any())).willReturn(mockUrl);

            //when
            List<String> result = photoStore.uploadFiles(files);

            //then
            for (String actual : result) {
                assertThat(actual).isEqualTo(mockUrl.toString());
            }
        }

        @Test
        @DisplayName("이미지가 한장도 없으면 빈 List를 응답한다")
        void NoImages() {
            //given
            List<MultipartFile> files = new ArrayList<>();

            //when
            List<String> result = photoStore.uploadFiles(files);

            //then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("파일이 null 이면 실패한다")
        void failByNullFile() {
            //given
            List<MultipartFile> files = new ArrayList<>();
            files.add(null);

            //when, then
            assertThatThrownBy(() -> photoStore.uploadFiles(files))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.EMPTY_FILE_EX.getMessage());
        }

        @Test
        @DisplayName("빈 파일이면 실패한다")
        void failByEmptyFile() {
            //given
            List<MultipartFile> files = new ArrayList<>();
            MockMultipartFile emptyFile = new MockMultipartFile("files", "hello1.png", "image/bmp", new byte[]{});
            files.add(emptyFile);

            //when, then
            assertThatThrownBy(() -> photoStore.uploadFiles(files))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.EMPTY_FILE_EX.getMessage());
        }
    }

    private String createUploadLink(String storeUrl) {
        return String.format(
                "https://kr.object.ncloudstorage.com/%s/%s",
                BUKET,
                storeUrl
        );
    }

    private String createStoreFilename(String originalFilename) {
        String ext = getExt(originalFilename);
        return UUID.randomUUID() + ext;
    }

    private String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index);
    }
}
