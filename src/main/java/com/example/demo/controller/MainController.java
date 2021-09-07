package com.example.demo.controller;

import com.example.demo.model.Driver;
import com.example.demo.model.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class MainController {
    private final DriverRepository driverRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewDriver(@RequestParam String firstname,
                        @RequestParam String lastname,
                        @RequestParam int wins,
                        @RequestParam int poles,
                        @RequestParam int fastest,
                        @RequestParam double points) {
        Driver driver = new Driver();
        driver.setFirstName(firstname);
        driver.setLastName(lastname);
        driver.setWins(wins);
        driver.setPoles(poles);
        driver.setFastestLaps(fastest);
        driver.setPoints(points);
        driverRepository.save(driver);
        return "Saved";
    }

    @PostMapping(path = "/addDriver")
    public @ResponseBody String addNewDriver(@RequestBody Driver driver) {
        driverRepository.save(driver);
        return "Saved";
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

    @GetMapping(path = "/get-all-lastname-{lastname}")
    public Iterable<Driver> getDriversWithLastName(@PathVariable String lastName) {
        return driverRepository.findAllByLastName(lastName);
    }

    @GetMapping(path = "/most-wins")
    public Iterable<Driver> getDriverWithMostWins() {
        return driverRepository.findFirstByOrderByWinsDesc();
    }

    @GetMapping(path = "/wins")
    public @ResponseBody Iterable<Driver> winsBetween(@RequestParam int from, @RequestParam int to) {
        return driverRepository.findDriversByWinsBetween(from, to);
    }

    @GetMapping(path = "/points")
    public @ResponseBody Iterable<Driver> pointsBetween(@RequestParam int from, @RequestParam int to) {
        return driverRepository.findDriversByPointsBetween(from, to);
    }

    @GetMapping(path = "/most-points")
    public @ResponseBody Iterable<Driver> getDriverWithMostPoints() {
        return driverRepository.findFirstByOrderByPointsDesc();
    }

    @GetMapping(path = "/sort-by-wins")
    public @ResponseBody Iterable<Driver> getDriversSortedByWins() {
        return driverRepository.findAllByOrderByWinsDesc();
    }

    @GetMapping(path = "/sort-by-points")
    public @ResponseBody Iterable<Driver> getDriversSortedByPoints() {
        return driverRepository.findAllByOrderByPointsDesc();
    }

    @GetMapping(path = "/wins-above")
    public @ResponseBody Iterable<Driver> getDriversWithMoreWinsThan(int n) {
        return driverRepository.findDriversByWinsAfter(n);
    }

    @GetMapping(path = "/wins-below")
    public @ResponseBody Iterable<Driver> getDriversWithLessWinsThan(int n) {
        return driverRepository.findDriversByWinsBefore(n);
    }

    @DeleteMapping(path = "/del/{id}")
    public @ResponseBody String deleteDriver(@PathVariable Long id) {
        driverRepository.deleteById(id);
        return "record deleted";
    }
}
