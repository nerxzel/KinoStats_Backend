package com.mooncowpines.KinoStats.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mooncowpines.KinoStats.Model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    
}
