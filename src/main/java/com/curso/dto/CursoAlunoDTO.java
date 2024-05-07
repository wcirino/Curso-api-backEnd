package com.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoAlunoDTO {
    private Long cursoID;
    private String curso;
    private String nomeAluno;
    private Long numMatricula;
    private String descricao;
    private Integer duracaoHora;
    private String instrutor;
    private Integer categoria;
    private String nivelDificuldade;

}
