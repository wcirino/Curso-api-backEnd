package com.curso.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cancelamento_AlunoMQ {
	
    private Long idInscricao;
    private String nomeAluno;
    private String curso_titulo;
    private Date dataalteracao;

}
