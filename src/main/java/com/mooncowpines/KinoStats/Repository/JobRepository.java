package com.mooncowpines.KinoStats.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mooncowpines.KinoStats.Model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{
    
}
