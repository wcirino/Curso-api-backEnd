package com.curso.repository.custom;

import java.math.BigDecimal;
import java.math.BigInteger;
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
                "A.NumMatricula, " +
                "M.Nome AS MetodoPagamento, " +
                "I.DataIniciarCurso, " +
                "I.ValorPago " +
                "FROM " +
                "Inscricoes I " +
                "INNER JOIN Curso C ON I.CursoID = C.id " +
                "INNER JOIN Aluno A ON I.AlunoID = A.id " +
                "INNER JOIN StatusPagamento S ON I.StatusPagamentoID = S.ID " +
                "INNER JOIN MetodoPagamento M ON I.MetodoPagamentoID = M.ID";

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
            inscricao.setNumeroCurso((Long) row[3]);
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

    public Page<CursoAlunoDTO> listarCursosDoAluno(Long alunoId, Pageable pageable) {
        String sql = "SELECT " +
                "C.id AS CursoID, " +
                "C.Titulo AS Curso, " +
                "A.Nome AS NomeAluno, " +
                "A.NumMatricula AS NumMatricula, " +
                "C.Descricao AS Descricao, " +
                "C.DuracaoHora AS DuracaoHora, " +
                "C.Instrutor AS Instrutor, " +
                "C.Categoria AS Categoria, " +
                "C.NivelDificuldade AS NivelDificuldade " +
                "FROM " +
                "Curso C " +
                "INNER JOIN Inscricoes I ON C.id = I.CursoID " +
                "INNER JOIN Aluno A ON I.AlunoID = A.id " +
                "WHERE " +
                "I.AlunoID = :alunoId";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("alunoId", alunoId);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<CursoAlunoDTO> result = new ArrayList<>();
        List<Object[]> rows = query.getResultList();
        for (Object[] row : rows) {
            CursoAlunoDTO cursoAluno = new CursoAlunoDTO();
            cursoAluno.setCursoID(((BigInteger) row[0]).longValue());
            cursoAluno.setCurso((String) row[1]);
            cursoAluno.setNomeAluno((String) row[2]);
            cursoAluno.setNumMatricula(((BigInteger) row[3]).longValue());
            cursoAluno.setDescricao((String) row[4]);
            cursoAluno.setDuracaoHora((Integer) row[5]);
            cursoAluno.setInstrutor((String) row[6]);
            cursoAluno.setCategoria((Integer) row[7]);
            cursoAluno.setNivelDificuldade((String) row[8]);
            result.add(cursoAluno);
        }

        return new PageImpl<>(result, pageable, result.size());
    }

    public Page<AlunoPorCursoDTO> listarAlunosPorCurso(Long cursoId, Pageable pageable) {
        String sql = "SELECT " +
                "A.id AS AlunoID, " +
                "A.Nome AS NomeAluno, " +
                "A.NumMatricula AS NumMatricula, " +
                "C.Titulo AS TituloCurso, " +
                "C.Descricao AS DescricaoCurso " +
                "FROM " +
                "Aluno A " +
                "INNER JOIN Inscricoes I ON A.id = I.AlunoID " +
                "INNER JOIN Curso C ON I.CursoID = C.id " +
                "WHERE " +
                "I.CursoID = :cursoId";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("cursoId", cursoId);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> resultList = query.getResultList();

        List<AlunoPorCursoDTO> result = resultList.stream().map(row -> {
            AlunoPorCursoDTO dto = new AlunoPorCursoDTO();
            dto.setAlunoID((Long) row[0]);
            dto.setNomeAluno((String) row[1]);
            dto.setNumMatricula((Long) row[2]);
            dto.setTituloCurso((String) row[3]);
            dto.setDescricaoCurso((String) row[4]);
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(result, pageable, result.size());
    }
}
