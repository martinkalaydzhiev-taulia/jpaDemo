package com.example.demo;

import com.example.demo.controller.F1DriverStatsController;
import com.example.demo.model.Driver;
import com.example.demo.model.DriverRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(F1DriverStatsController.class)
public class F1DriverStatsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverRepository driverRepository;

    @Test
    public void getDriverByID() throws Exception {
        doReturn(Optional.of(
                new Driver(1L, "Michael", "Schumacher", 91, 68, 70, 2500)))
                .when(driverRepository).findById(anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/1")
        )
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json("{\"id\":1,\"firstName\":Michael,\"lastName\":Schumacher,\"wins\":91,\"poles\":68,\"fastestLaps\":70,\"points\":2500.0}"));

        verify(driverRepository).findById(1L);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversByFirstName() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/get-all-firstname-Michael")
        )
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json("[]"));

        verify(driverRepository).findAllByFirstName("Michael");
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversByFirstName2() throws Exception {
        doReturn(Arrays.asList(
                new Driver(1L, "Michael", "asd", 10, 12, 0, 50),
                new Driver(2L, "Michael", "Schu", 90, 80, 70, 1000))
        )
                .when(driverRepository).findAllByFirstName("Michael");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/get-all-firstname-Michael")
        )
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .json("[{\"id\":1,\"firstName\":\"Michael\",\"lastName\":\"asd\",\"wins\":10,\"poles\":12,\"fastestLaps\":0,\"points\":50.0},{\"id\":2,\"firstName\":\"Michael\",\"lastName\":\"Schu\",\"wins\":90,\"poles\":80,\"fastestLaps\":70,\"points\":1000.0}]\n"));

        verify(driverRepository).findAllByFirstName("Michael");
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getAllDrivers() throws Exception {
        doReturn(Arrays.asList(
                new Driver(3L, "M", "S", 30, 0, 10, 390),
                new Driver(10L, "M", "H", 10, 9, 8, 221))
        )
                .when(driverRepository).findAll();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/all")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":3,\"firstName\":\"M\",\"lastName\":\"S\",\"wins\":30,\"poles\":0,\"fastestLaps\":10,\"points\":390.0},{\"id\":10,\"firstName\":\"M\",\"lastName\":\"H\",\"wins\":10,\"poles\":9,\"fastestLaps\":8,\"points\":221.0}]\n"));

        verify(driverRepository).findAll();
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversByLastName() throws Exception {
        doReturn(Arrays.asList(
                new Driver(3L, "Graham", "Hill", 30, 0, 10, 390),
                new Driver(10L, "Damon", "Hill", 10, 9, 8, 221))
        )
                .when(driverRepository).findAllByLastName("Hill");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/get-all-lastname-Hill")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":3,\"firstName\":\"Graham\",\"lastName\":\"Hill\",\"wins\":30,\"poles\":0,\"fastestLaps\":10,\"points\":390.0},{\"id\":10,\"firstName\":\"Damon\",\"lastName\":\"Hill\",\"wins\":10,\"poles\":9,\"fastestLaps\":8,\"points\":221.0}]\n"));

        verify(driverRepository).findAllByLastName("Hill");
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversWithMostWins() throws Exception {
        doReturn(Arrays.asList(
                new Driver(3L, "Michael", "Schumacher", 91, 68, 65, 2500))
        )
                .when(driverRepository).findFirstByOrderByWinsDesc();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/most-wins")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":3,\"firstName\":\"Michael\",\"lastName\":\"Schumacher\",\"wins\":91,\"poles\":68,\"fastestLaps\":65,\"points\":2500.0}]\n"));

        verify(driverRepository).findFirstByOrderByWinsDesc();
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversWithWinsInRange() throws Exception {
        doReturn(Arrays.asList(
                new Driver(3L, "Michael", "Schumacher", 91, 68, 65, 2500))
        )
                .when(driverRepository).findDriversByWinsBetween(50, 95);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/wins")
                        .param("from", "50")
                        .param("to", "95")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":3,\"firstName\":\"Michael\",\"lastName\":\"Schumacher\",\"wins\":91,\"poles\":68,\"fastestLaps\":65,\"points\":2500.0}]\n"));

        verify(driverRepository).findDriversByWinsBetween(50, 95);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversWithMostPoints() throws Exception {
        doReturn(Arrays.asList(
                new Driver(3L, "Michael", "Schumacher", 91, 68, 65, 2500))
        )
                .when(driverRepository).findFirstByOrderByPointsDesc();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/most-points")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":3,\"firstName\":\"Michael\",\"lastName\":\"Schumacher\",\"wins\":91,\"poles\":68,\"fastestLaps\":65,\"points\":2500.0}]\n"));

        verify(driverRepository).findFirstByOrderByPointsDesc();
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void sortedByPoints() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/sort-by-points")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(driverRepository).findAllByOrderByPointsDesc();
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void sortedByWins() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/sort-by-wins")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(driverRepository).findAllByOrderByWinsDesc();
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversWithMoreWinsThan() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/wins-above").param("n", "0")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(driverRepository).findDriversByWinsAfter(0);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void getDriversWithLessWinsThan() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/f1stats/wins-below").param("n", "10")
        )
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(driverRepository).findDriversByWinsBefore(10);
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void addDriver() throws Exception {
        doReturn(new Driver(3L, "Michael", "Schumacher", 91, 68, 65, 2500.0))
                .when(driverRepository)
                .save(any());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/f1stats/addDriver")
                        .content("{\"firstName\":\"Michael\",\"lastName\":\"Schumacher\",\"wins\":91,\"poles\":68,\"fastestLaps\":65,\"points\":2500.0}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":3,\"firstName\":\"Michael\",\"lastName\":\"Schumacher\",\"wins\":91,\"poles\":68,\"fastestLaps\":65,\"points\":2500.0}"));
        verify(driverRepository).save(new Driver(null, "Michael", "Schumacher", 91, 68, 65, 2500.0));
        verifyNoMoreInteractions(driverRepository);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteDriver() {

    }

    @Test
    void updateDriver() {

    }
}
