package com.curso.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoDTO {

    private Long id;
    private Long cursoID;
    private Long alunoID;
    private Date dataInscricao;
    private Integer statusPagamentoID;
    private Integer metodoPagamentoID;
    private BigDecimal valorPago;
    private Date dataIniciarCurso;

}
