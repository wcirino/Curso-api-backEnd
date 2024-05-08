package com.curso.controller;

import com.curso.dto.CursoDTO;
import com.curso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("find/all")
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        List<CursoDTO> cursos = cursoService.listarCursos();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<CursoDTO> obterDetalhesCurso(@PathVariable Long id) {
        CursoDTO cursoDTO = cursoService.obterDetalhesCurso(id);
        return new ResponseEntity<>(cursoDTO, HttpStatus.OK);
    }

    @PostMapping("/cursor/inserir")
    public ResponseEntity<CursoDTO> criarCurso(@RequestBody CursoDTO cursoDTO) {
        CursoDTO novoCurso = cursoService.criarCurso(cursoDTO);
        return new ResponseEntity<>(novoCurso, HttpStatus.CREATED);
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<CursoDTO> atualizarCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        CursoDTO cursoAtualizado = cursoService.atualizarCurso(id, cursoDTO);
        return new ResponseEntity<>(cursoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> excluirCurso(@PathVariable Long id) {
        cursoService.excluirCurso(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}