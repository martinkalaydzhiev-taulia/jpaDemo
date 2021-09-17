package com.example.demo.repository;

import com.example.demo.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DriverRepositoryTestEmbedded {

    @Autowired
    private DriverRepository driverRepository;

    private static final Driver MSC = Driver.builder()
            .firstName("Michael")
            .lastName("Schumacher")
            .wins(92)
            .fastestLaps(65)
            .poles(68)
            .points(2423)
            .build();

    private static final Driver SAI = Driver.builder()
            .firstName("Carlos")
            .lastName("Sainz")
            .wins(0)
            .fastestLaps(0)
            .poles(0)
            .points(135)
            .build();

    private static final Driver VER = Driver.builder()
            .firstName("Max")
            .lastName("Verstappen")
            .wins(15)
            .fastestLaps(13)
            .poles(10)
            .points(783)
            .build();

    private static final Driver RSC = Driver.builder()
            .firstName("Ralf")
            .lastName("Schumacher")
            .wins(4)
            .fastestLaps(5)
            .poles(3)
            .points(178)
            .build();


    private static List<Driver> drivers = Arrays.asList(SAI, VER, RSC, MSC);


    @Test
    public void shouldSaveDriver() {
        Driver driver = drivers.get(0);
        Driver savedDriver = driverRepository.save(driver);
        assertThat(savedDriver).usingRecursiveComparison().ignoringFields("id").isEqualTo(driver);
    }

    @Test
    public void shouldGetByID() {
        Driver sainz = driverRepository.save(drivers.get(0));
        Driver verstappen = driverRepository.save(drivers.get(1));
        Optional<Driver> verstappenSaved = driverRepository.findById(verstappen.getId());
        assertThat(verstappenSaved).contains(verstappen);
    }

    @Test
    public void shouldGetByLastName() {
        Driver ralf = driverRepository.save(drivers.get(2));
        Driver msc = driverRepository.save(drivers.get(3));
        Iterable<Driver> sch = driverRepository.findAllByLastName(msc.getLastName());
        assertThat(Arrays.asList(ralf, msc)).usingRecursiveComparison().isEqualTo(sch);
    }

    @Test
    public void shouldGetByMostWins() {
        driverRepository.save(drivers.get(0));
        driverRepository.save(drivers.get(1));
        driverRepository.save(drivers.get(2));
        Driver msc = driverRepository.save(drivers.get(3));
        assertThat(Collections.singletonList(msc)).usingRecursiveComparison().ignoringFields("id").isEqualTo(driverRepository.findFirstByOrderByWinsDesc());
    }
}
