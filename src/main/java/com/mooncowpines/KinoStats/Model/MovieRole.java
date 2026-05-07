package com.mooncowpines.KinoStats.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film_job_person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "api_id")
    private Film film;
}
