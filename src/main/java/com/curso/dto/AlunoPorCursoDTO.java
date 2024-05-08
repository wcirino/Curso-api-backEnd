package com.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoPorCursoDTO {

    private Integer alunoID;
    private String nomeAluno;
    private Long numMatricula;
    private String tituloCurso;
    private String descricaoCurso;
	
}
