package com.inq.wishhair.wesharewishhair.photo.service;

import com.inq.wishhair.wesharewishhair.global.base.ServiceTest;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("PhotoService test - SpringBootTest")
public class PhotoServiceTest extends ServiceTest {

    @MockBean
    private PhotoStore photoStore;

    @Autowired
    private PhotoService photoService;

    
}
