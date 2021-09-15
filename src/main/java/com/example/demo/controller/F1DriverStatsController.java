package com.example.demo.controller;

import com.example.demo.model.Driver;
import com.example.demo.model.Team;
import com.example.demo.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/f1stats")
@RequiredArgsConstructor
public class F1DriverStatsController {
    private final DriverRepository driverRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<Driver> addDriver(@RequestParam String firstname,
                                     @RequestParam String lastname,
                                     @RequestParam int wins,
                                     @RequestParam int poles,
                                     @RequestParam int fastest,
                                     @RequestParam double points) {
        Team team = Team.builder()
                .teamName("Ferrari")
                .build();
        Driver driver = Driver.builder().currentTeam(team).build();
        driver.setFirstName(firstname);
        driver.setLastName(lastname);
        driver.setWins(wins);
        driver.setPoles(poles);
        driver.setFastestLaps(fastest);
        driver.setPoints(points);
        driverRepository.save(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);
    }

    @PostMapping(path = "/addDriver")
    public @ResponseBody
    ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverRepository.save(driver);
        savedDriver.setCurrentTeam(Team.builder().teamName("Ferrari").build());
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public Iterable<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Driver> getDriverById(@PathVariable Long id) {
        return driverRepository.findById(id);
    }

    @GetMapping(path = "/get-all-firstname-{firstName}")
    public Iterable<Driver> getDriversWithFirstName(@PathVariable String firstName) {
        return driverRepository.findAllByFirstName(firstName);
    }

    @GetMapping(path = "/get-all-lastname-{lastName}")
    public Iterable<Driver> getDriversWithLastName(@PathVariable String lastName) {
        return driverRepository.findAllByLastName(lastName);
    }

    @GetMapping(path = "/most-wins")
    public Iterable<Driver> getDriverWithMostWins() {
        return driverRepository.findFirstByOrderByWinsDesc();
    }

    @GetMapping(path = "/wins")
    public @ResponseBody
    Iterable<Driver> winsBetween(@RequestParam int from, @RequestParam int to) {
        return driverRepository.findDriversByWinsBetween(from, to);
    }

    @GetMapping(path = "/most-points")
    public @ResponseBody
    Iterable<Driver> getDriverWithMostPoints() {
        return driverRepository.findFirstByOrderByPointsDesc();
    }

    @GetMapping(path = "/sort-by-wins")
    public @ResponseBody
    Iterable<Driver> getDriversSortedByWins() {
        return driverRepository.findAllByOrderByWinsDesc();
    }

    @GetMapping(path = "/sort-by-points")
    public @ResponseBody
    Iterable<Driver> getDriversSortedByPoints() {
        return driverRepository.findAllByOrderByPointsDesc();
    }

    @GetMapping(path = "/wins-above")
    public @ResponseBody
    Iterable<Driver> getDriversWithMoreWinsThan(int n) {
        return driverRepository.findDriversByWinsAfter(n);
    }

    @GetMapping(path = "/wins-below")
    public @ResponseBody
    Iterable<Driver> getDriversWithLessWinsThan(int n) {
        return driverRepository.findDriversByWinsBefore(n);
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody
    ResponseEntity<Driver> deleteDriver(@PathVariable Long id) {
        driverRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Driver> updateDriverWins(
            @PathVariable Long id,
            @RequestParam Optional<String> firstName,
            @RequestParam Optional<String> lastName,
            @RequestParam Optional<Integer> wins,
            @RequestParam Optional<Integer> poles,
            @RequestParam Optional<Integer> fastestLaps,
            @RequestParam Optional<Double> points) {
        return driverRepository.findById(id)
                .map(driver -> {
                    firstName.ifPresent(driver::setFirstName);
                    lastName.ifPresent(driver::setLastName);
                    wins.ifPresent(driver::setWins);
                    poles.ifPresent(driver::setPoles);
                    fastestLaps.ifPresent(driver::setFastestLaps);
                    points.ifPresent(driver::setPoints);
                    driverRepository.save(driver);

                    return ok(driver);
                })
                .orElseGet(() -> notFound().build());
    }
}
