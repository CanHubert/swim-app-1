package com.can.app.swim.swimapp.repository;

import com.can.app.swim.swimapp.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "*", maxAge = 3600)
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findByNameIn(List<String> names);
}
