package com.example.demo;

import com.example.demo.controller.F1DriverStatsController;
import com.example.demo.model.Driver;
import com.example.demo.model.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(F1DriverStatsController.class)
public class F1DriverStatsControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverRepository driverRepository;

    @Test
    public void getDriverByID() throws Exception {
        doReturn(Optional.of(new Driver())).when(driverRepository).findById(anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/1")
        )
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json("{\"id\":null,\"firstName\":null,\"lastName\":null,\"wins\":0,\"poles\":0,\"fastestLaps\":0,\"points\":0.0}"));

        verify(driverRepository).findById(1L);
        verifyNoMoreInteractions(driverRepository);
    }

}
