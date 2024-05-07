package com.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.entity.Inscricao;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
  
    List<Inscricao> findByCursoID(Long cursoId);

    List<Inscricao> findByAlunoID(Long alunoId);
}
