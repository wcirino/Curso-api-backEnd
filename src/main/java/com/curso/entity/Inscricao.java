package com.curso.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inscricoes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "curso_id")
    private Long cursoID;

    @Column(name = "aluno_id")
    private Long alunoID;

    @Column(name = "data_inscricao")
    private Date dataInscricao;

    @Column(name = "status_pagamento_id")
    private Integer statusPagamentoID;

    @Column(name = "metodo_pagamento_id")
    private Integer metodoPagamentoID;

    @Column(name = "valor_pago", precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Column(name = "data_iniciar_curso")
    private Date dataIniciarCurso;

}
