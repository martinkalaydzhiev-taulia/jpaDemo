package com.example.demo.repository;

import com.example.demo.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DriverRepositoryTestEmbedded {

    @Autowired
    private DriverRepository driverRepository;

    private static List<Driver> drivers = Arrays.asList(
            Driver.builder()
                    .firstName("Carlos")
                    .lastName("Sainz")
                    .wins(0)
                    .fastestLaps(0)
                    .poles(0)
                    .points(135)
                    .build(),
            Driver.builder()
                    .firstName("Max")
                    .lastName("Verstappen")
                    .wins(15)
                    .fastestLaps(13)
                    .poles(10)
                    .points(783)
                    .build(),
            Driver.builder()
                    .firstName("Ralf")
                    .lastName("Schumacher")
                    .wins(4)
                    .fastestLaps(5)
                    .poles(3)
                    .points(178)
                    .build(),
            Driver.builder()
                    .firstName("Michael")
                    .lastName("Schumacher")
                    .wins(92)
                    .fastestLaps(65)
                    .poles(68)
                    .points(2423)
                    .build());

    @Test
    public void shouldSaveDriver() {
        Driver driver = drivers.get(0);
        Driver savedDriver = driverRepository.save(driver);
        assertThat(savedDriver).usingRecursiveComparison().ignoringFields("id").isEqualTo(driver);
    }

    @Test
    public void shouldGetByID() {
        Driver carlos = driverRepository.save(drivers.get(0));
        Driver max = driverRepository.save(drivers.get(1));
        Optional<Driver> maxSaved = driverRepository.findById(max.getId());
        assertThat(maxSaved).contains(max);
    }

    @Test
    public void shouldGetByLastName() {
        Driver ralf = driverRepository.save(drivers.get(2));
        Driver msc = driverRepository.save(drivers.get(3));
        Iterable<Driver> sch = driverRepository.findAllByLastName("Schumacher");
        assertThat(Arrays.asList(ralf, msc)).usingRecursiveComparison().isEqualTo(sch);
    }

    @Test
    public void shouldGetByMostWins() {
        driverRepository.save(drivers.get(0));
        driverRepository.save(drivers.get(1));
        driverRepository.save(drivers.get(2));
        Driver msc = driverRepository.save(drivers.get(3));
        assertThat(Arrays.asList(msc)).usingRecursiveComparison().ignoringFields("id").isEqualTo(driverRepository.findFirstByOrderByWinsDesc());
    }
}
