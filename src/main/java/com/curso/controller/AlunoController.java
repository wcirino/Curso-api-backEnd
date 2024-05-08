package com.curso.controller;

import com.curso.dto.AlunoDTO;
import com.curso.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("find/all")
    public ResponseEntity<List<AlunoDTO>> listarAlunos() {
        List<AlunoDTO> alunos = alunoService.listarAlunos();
        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<AlunoDTO> obterDetalhesAluno(@PathVariable Long id) {
        AlunoDTO alunoDTO = alunoService.obterDetalhesAluno(id);
        return new ResponseEntity<>(alunoDTO, HttpStatus.OK);
    }

    @PutMapping("atualiza/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO alunoAtualizado = alunoService.atualizarAluno(id, alunoDTO);
        return new ResponseEntity<>(alunoAtualizado, HttpStatus.OK);
    }

}
