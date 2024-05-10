package com.curso.repository.custom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.curso.dto.AlunoPorCursoDTO;
import com.curso.dto.CursoAlunoDTO;
import com.curso.dto.InscricaoDetalheDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class InscricaoCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<InscricaoDetalheDTO> listarInscricoes(Pageable pageable) {
        String sql = "SELECT " +
                "I.id AS InscricaoID, " +
                "C.Titulo AS Curso, " +
                "A.Nome AS Aluno, " +
                "C.id AS NumeroCurso, " +
                "I.DataInscricao, " +
                "S.Nome AS StatusPagamento, " +
                "A.num_matricula, " +
                "M.Nome AS MetodoPagamento, " +
                "I.DataIniciarCurso, " +
                "I.ValorPago " +
                "FROM " +
                "inscricoes I " +
                "INNER JOIN curso C ON I.CursoID = C.id " +
                "INNER JOIN aluno A ON I.AlunoID = A.id " +
                "INNER JOIN status_pagamento S ON I.status_pagamento = S.ID " +
                "INNER JOIN metodo_pagamento M ON I.metodo_pagamento = M.ID";

        Query query = entityManager.createNativeQuery(sql);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<InscricaoDetalheDTO> result = new ArrayList<>();
        List<Object[]> rows = query.getResultList();
        for (Object[] row : rows) {
            InscricaoDetalheDTO inscricao = new InscricaoDetalheDTO();
            inscricao.setInscricaoID((Long) row[0]);
            inscricao.setCurso((String) row[1]);
            inscricao.setAluno((String) row[2]);
            inscricao.setNumeroCurso((Integer) row[3]);
            inscricao.setDataInscricao((Date) row[4]);
            inscricao.setStatusPagamento((String) row[5]);
            inscricao.setNumMatricula((Long) row[6]);
            inscricao.setMetodoPagamento((String) row[7]);
            inscricao.setDataIniciarCurso((Date) row[8]);
            inscricao.setValorPago((BigDecimal) row[9]);
            result.add(inscricao);
        }

        return new PageImpl<>(result, pageable, result.size());
    }

    public Page<CursoAlunoDTO> listarCursosDoAluno(Long alunoId, String nomeAluno, Pageable pageable) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ")
                  .append("C.id AS CursoID, ")
                  .append("C.Titulo AS Curso, ")
                  .append("A.Nome AS NomeAluno, ")
                  .append("A.num_matricula AS NumMatricula, ")
                  .append("C.Descricao AS Descricao, ")
                  .append("C.DuracaoHora AS DuracaoHora, ")
                  .append("C.Instrutor AS Instrutor, ")
                  .append("C.Categoria AS Categoria, ")
                  .append("C.NivelDificuldade AS NivelDificuldade ")
                  .append("FROM ")
                  .append("curso C ")
                  .append("INNER JOIN inscricoes I ON C.id = I.CursoID ")
                  .append("INNER JOIN aluno A ON I.AlunoID = A.id ")
                  .append("WHERE 1=1 ");

        if (alunoId != null) {
            sqlBuilder.append("AND I.AlunoID = :alunoId ");
        }

        if (nomeAluno != null && !nomeAluno.isEmpty()) {
            sqlBuilder.append("AND A.Nome LIKE :nomeAluno ");
        }

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());

        if (alunoId != null) {
            query.setParameter("alunoId", alunoId);
        }

        if (nomeAluno != null && !nomeAluno.isEmpty()) {
            query.setParameter("nomeAluno", "%" + nomeAluno + "%");
        }

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<CursoAlunoDTO> result = new ArrayList<>();
        List<Object[]> rows = query.getResultList();
        for (Object[] row : rows) {
            CursoAlunoDTO cursoAluno = new CursoAlunoDTO();
            cursoAluno.setCursoID(((Integer) row[0]).longValue());
            cursoAluno.setCurso((String) row[1]);
            cursoAluno.setNomeAluno((String) row[2]);
            cursoAluno.setNumMatricula(((Long) row[3]).longValue());
            cursoAluno.setDescricao((String) row[4]);
            cursoAluno.setDuracaoHora((Integer) row[5]);
            cursoAluno.setInstrutor((String) row[6]);
            cursoAluno.setCategoria((Integer) row[7]);
            cursoAluno.setNivelDificuldade((String) row[8]);
            result.add(cursoAluno);
        }

        return new PageImpl<>(result, pageable, result.size());
    }


    public Page<AlunoPorCursoDTO> listarAlunosPorCurso(Long cursoId, String tituloCurso, Pageable pageable) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ")
                  .append("A.id AS AlunoID, ")
                  .append("A.Nome AS NomeAluno, ")
                  .append("A.num_matricula AS NumMatricula, ")
                  .append("C.Titulo AS TituloCurso, ")
                  .append("C.Descricao AS DescricaoCurso ")
                  .append("FROM ")
                  .append("aluno A ")
                  .append("INNER JOIN inscricoes I ON A.id = I.AlunoID ")
                  .append("INNER JOIN curso C ON I.CursoID = C.id ")
                  .append("WHERE ");

        if (cursoId != null) {
            sqlBuilder.append("I.CursoID = :cursoId ");
        }

        if (tituloCurso != null && !tituloCurso.isEmpty()) {
            if (cursoId != null) {
                sqlBuilder.append("AND ");
            }
            sqlBuilder.append("C.Titulo LIKE :tituloCurso ");
        }

        Query query = entityManager.createNativeQuery(sqlBuilder.toString());

        if (cursoId != null) {
            query.setParameter("cursoId", cursoId);
        }

        if (tituloCurso != null && !tituloCurso.isEmpty()) {
            query.setParameter("tituloCurso", "%" + tituloCurso + "%");
        }

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> resultList = query.getResultList();

        List<AlunoPorCursoDTO> result = resultList.stream().map(row -> {
            AlunoPorCursoDTO dto = new AlunoPorCursoDTO();
            dto.setAlunoID((Integer) row[0]);
            dto.setNomeAluno((String) row[1]);
            dto.setNumMatricula((Long) row[2]);
            dto.setTituloCurso((String) row[3]);
            dto.setDescricaoCurso((String) row[4]);
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(result, pageable, result.size());
    }
    
    public InscricaoDetalheDTO buscarInscricaoPorId(Long inscricaoId) {
        String sql = "SELECT " +
                "I.id AS InscricaoID, " +
                "C.Titulo AS Curso, " +
                "A.Nome AS Aluno, " +
                "C.id AS NumeroCurso, " +
                "I.DataInscricao, " +
                "S.Nome AS StatusPagamento, " +
                "A.num_matricula, " +
                "M.Nome AS MetodoPagamento, " +
                "I.DataIniciarCurso, " +
                "I.ValorPago " +
                "FROM " +
                "inscricoes I " +
                "INNER JOIN curso C ON I.CursoID = C.id " +
                "INNER JOIN aluno A ON I.AlunoID = A.id " +
                "INNER JOIN status_pagamento S ON I.status_pagamento = S.ID " +
                "INNER JOIN metodo_pagamento M ON I.metodo_pagamento = M.ID " +
                "WHERE I.id = :inscricaoId";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("inscricaoId", inscricaoId);

        Object[] row = (Object[]) query.getSingleResult();

        InscricaoDetalheDTO inscricao = new InscricaoDetalheDTO();
        inscricao.setInscricaoID((Long) row[0]);
        inscricao.setCurso((String) row[1]);
        inscricao.setAluno((String) row[2]);
        inscricao.setNumeroCurso((Integer) row[3]);
        inscricao.setDataInscricao((Date) row[4]);
        inscricao.setStatusPagamento((String) row[5]);
        inscricao.setNumMatricula((Long) row[6]);
        inscricao.setMetodoPagamento((String) row[7]);
        inscricao.setDataIniciarCurso((Date) row[8]);
        inscricao.setValorPago((BigDecimal) row[9]);

        return inscricao;
    }


}
