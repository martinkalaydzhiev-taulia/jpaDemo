package com.example.demo.repository;

import com.example.demo.model.Driver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Iterable<Driver> findAllByFirstName(String name);
    Iterable<Driver> findAllByLastName(String name);
    Iterable<Driver> findDriversByWinsBefore(int n);
    Iterable<Driver> findDriversByWinsAfter(int n);
    Iterable<Driver> findFirstByOrderByWinsDesc();
    Iterable<Driver> findFirstByOrderByPointsDesc();
    Iterable<Driver> findDriversByWinsBetween(int from, int to);
    Iterable<Driver> findAllByOrderByWinsDesc();
    Iterable<Driver> findAllByOrderByPointsDesc();

    List<Driver> findAll();
}
