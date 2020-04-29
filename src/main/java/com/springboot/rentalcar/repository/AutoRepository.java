package com.springboot.rentalcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rentalcar.entity.Auto;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {

}
