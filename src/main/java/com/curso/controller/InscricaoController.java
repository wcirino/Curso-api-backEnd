package com.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curso.dto.AlunoPorCursoDTO;
import com.curso.dto.CursoAlunoDTO;
import com.curso.dto.InscricaoDTO;
import com.curso.dto.InscricaoDetalheDTO;
import com.curso.service.InscricaoService;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping("/aluno/inserir")
    public ResponseEntity<InscricaoDTO> inscreverAlunoCurso(@RequestBody InscricaoDTO inscricaoDTO) {
        InscricaoDTO novaInscricao = inscricaoService.inscreverAlunoCurso(inscricaoDTO);
        return new ResponseEntity<>(novaInscricao, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarInscricao(@PathVariable Long id) {
        inscricaoService.cancelarInscricao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/lista-inscricao")
    public ResponseEntity<Page<InscricaoDetalheDTO>> listarInscricoesPorCurso(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Pageable pageable) {
    	pageable = PageRequest.of(page, size);
        Page<InscricaoDetalheDTO> inscricoes = inscricaoService.listarInscricoesCurso(pageable);
        return new ResponseEntity<>(inscricoes, HttpStatus.OK);
    }


    @GetMapping("/aluno/find")
    public ResponseEntity<Page<CursoAlunoDTO>> listarCursosDoAluno(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) String nomeAluno, 
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Pageable pageable) {
        pageable = PageRequest.of(page, size);
        Page<CursoAlunoDTO> cursos = inscricaoService.listarCursosDoAluno(alunoId, nomeAluno, pageable); 
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/curso/find")
    public ResponseEntity<Page<AlunoPorCursoDTO>> listarAlunosPorCurso(
            @RequestParam(required = false) Long cursoId,
            @RequestParam(required = false) String tituloCurso, 
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Pageable pageable) {
        pageable = PageRequest.of(page, size);
        Page<AlunoPorCursoDTO> alunos = inscricaoService.listarAlunosPorCurso(cursoId, tituloCurso, pageable);
        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }


}