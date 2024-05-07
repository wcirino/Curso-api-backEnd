package com.curso.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
	
}
