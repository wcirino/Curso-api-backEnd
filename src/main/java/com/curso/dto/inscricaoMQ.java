package com.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class inscricaoMQ {

	    private Long id;
	    private Long cursoId;
	    private String titulo_curso;
	    private Long alunoId;
	    private String nome_aluno;
	    private Integer statusPagamentoId;
	    private Integer metodoPagamentoId;
	    
	    	
}
