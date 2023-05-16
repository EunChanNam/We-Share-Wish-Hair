package com.inq.wishhair.wesharewishhair.user.utils;

import com.inq.wishhair.wesharewishhair.global.base.InfraTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DisplayName("FlaskConnector test - InfraTest")
public class FlaskConnectorTest extends InfraTest {

    private static final String DOMAIN = "https://localhost:5000";
    private static final String URL = "/fileupload";
    private static final String FILENAME = "hello1.png";

    @Mock
    private RestTemplate restTemplate;

    private FlaskConnector connector;

    @BeforeEach
    void setUp() {
        connector = new FlaskConnector(DOMAIN);
        ReflectionTestUtils.setField(connector, "restTemplate", restTemplate);
    }

    @Nested
    @DisplayName("입력받은 이미지에 대한 얼굴형을 분류한다")
    class detectFaceShape {
        @Test
        @DisplayName("얼굴형 분류에 성공한다")
        void success() throws IOException {
            //given
            MultipartFile file = MockMultipartFileUtils.createMultipartFile(FILENAME, "file");
            ResponseEntity<String> response = ResponseEntity.ok("OBLONG");
            given(restTemplate.postForEntity(eq(DOMAIN + URL), any(), eq(String.class)))
                    .willReturn(response);

            //when
            Tag result = connector.detectFaceShape(file);

            //then
            assertThat(result).isEqualTo(Tag.OBLONG);
        }

        @Test
        @DisplayName("빈 파일로 실패한다")
        void failByEmptyFile() {
            //given
            MultipartFile emptyFile = new MockMultipartFile("files", "hello1.png", "image/bmp", new byte[]{});
            MultipartFile nullFile = null;
            ErrorCode expectedError = ErrorCode.EMPTY_FILE_EX;

            //when, then
            assertThatThrownBy(() -> connector.detectFaceShape(emptyFile))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
            assertThatThrownBy(() -> connector.detectFaceShape(nullFile))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("Flask Server 오류로 실패한다")
        void failByFlaskServer() throws IOException {
            //given
            ErrorCode expectedError = ErrorCode.FLASK_SERVER_EXCEPTION;
            MultipartFile file = MockMultipartFileUtils.createMultipartFile(FILENAME, "file");
            given(restTemplate.postForEntity(eq(DOMAIN + URL), any(), eq(String.class)))
                    .willThrow(new WishHairException(expectedError));

            //when, then
            assertThatThrownBy(() -> connector.detectFaceShape(file))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(expectedError.getMessage());
        }

        @Test
        @DisplayName("Flask Server 의 잘못된 형식의 응답값으로 실패한다")
        void failByFlaskServerResponse() throws IOException {
            //given
            final String INVALID_RESPONSE = "INVALID";
            MultipartFile file = MockMultipartFileUtils.createMultipartFile(FILENAME, "file");
            ResponseEntity<String> response = ResponseEntity.ok(INVALID_RESPONSE);
            given(restTemplate.postForEntity(eq(DOMAIN + URL), any(), eq(String.class)))
                    .willReturn(response);

            //when, then
            assertThatThrownBy(() -> connector.detectFaceShape(file))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.FLASK_RESPONSE_ERROR.getMessage());
        }

        @Test
        @DisplayName("잘못된 응답 코드로 실패한다")
        void failByStatusCode() throws IOException {
            //given
            MultipartFile file = MockMultipartFileUtils.createMultipartFile(FILENAME, "file");
            ResponseEntity<String> response = ResponseEntity.status(400).body("OBLONG");
            given(restTemplate.postForEntity(eq(DOMAIN + URL), any(), eq(String.class)))
                    .willReturn(response);

            //when, then
            assertThatThrownBy(() -> connector.detectFaceShape(file))
                    .isInstanceOf(WishHairException.class)
                    .hasMessageContaining(ErrorCode.FLASK_RESPONSE_ERROR.getMessage());
        }
    }
}
