package com.curso.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoDetalheDTO {
    private Long inscricaoID;
    private String curso;
    private String aluno;
    private Long numeroCurso;
    private Date dataInscricao;
    private String statusPagamento;
    private Long numMatricula;
    private String metodoPagamento;
    private Date dataIniciarCurso;
    private BigDecimal valorPago;

}
