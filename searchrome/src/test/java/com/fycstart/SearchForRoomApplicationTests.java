package com.fycstart;

import com.fycstart.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SearchForRoomApplicationTests {


    @Autowired
    private SearchService searchService;

    @Test
    public void index() throws IOException {
        String houseId = "15";
        Boolean aBoolean = searchService.paddingIndex(houseId);
        System.out.println(aBoolean);

    }

}
