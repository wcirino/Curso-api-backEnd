package com.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusPagamentoMQ {
	   private Integer id;
	   private Integer statusPagamentoId;
	   private Integer metodoPagamentoId;
}
