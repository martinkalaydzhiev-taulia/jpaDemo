package com.example.demo.repository;

import com.example.demo.BaseTestContainers;
import com.example.demo.model.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DriverRepositoryTest extends BaseTestContainers {

    @Autowired
    private DriverRepository driverRepository;

    @Test
    public void shouldSaveDriver() {
        Driver driver = Driver.builder()
                .firstName("Kamui").lastName("Kobayashi").wins(0).fastestLaps(0).poles(0).points(23).build();
        Driver savedDriver = driverRepository.save(driver);
        assertThat(savedDriver).usingRecursiveComparison().isEqualTo(driver);
    }

    @Test
    public void updateWins() {
        Driver driver = Driver.builder().firstName("Kamui").lastName("Kobayashi").wins(0).fastestLaps(0).poles(0).points(23).build();
        Driver savedDriver = driverRepository.save(driver);
        savedDriver.setWins(1);
        Driver updatedDriver = driverRepository.save(savedDriver);
        Optional<Driver> d = driverRepository.findById(savedDriver.getId());
        List<Driver> listOfDrivers = driverRepository.findAll();
        Assertions.assertEquals(listOfDrivers.size(), 1);
        Assertions.assertTrue(d.isPresent());
        Assertions.assertEquals(d.get().getWins(), 1L);
    }

}
