package com.mooncowpines.KinoStats.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mooncowpines.KinoStats.Model.MovieRole;

@Repository
public interface MovieRoleRepository extends JpaRepository<MovieRole, Long>{
    
}
