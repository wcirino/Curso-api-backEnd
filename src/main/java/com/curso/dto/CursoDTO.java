package com.curso.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer duracaoHora;
    private String instrutor;
    private Integer categoria;
    private BigDecimal preco;
    private Date dataLancamento;
    private String nivelDificuldade;
    private String requisitos;

}
