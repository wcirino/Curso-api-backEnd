package com.curso.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

	@Value("${mq.queues.curso-inscricao-aluno}")
	private String curso_inscricao_alunolta_Fila;
	
	@Value("${mq.queues.curso-cancelamento-aluno}")
	private String curso_cancelamento_aluno;
	
	@Value("${mq.queues.status-pagamento}")
	private String status_pagamento;
	
	@Value("${mq.queues.email}")
	private String email;
	
	@Bean
	public Queue queueCursoInscricaoAlunoltaFila() {
		return new Queue(curso_inscricao_alunolta_Fila,true);
	}
	
	@Bean
	public Queue queueCursoCancelamentoAluno() {
		return new Queue(curso_cancelamento_aluno,true);
	}
	
	@Bean
	public Queue queueStatusPagamento() {
		return new Queue(status_pagamento,true);
	}
	
	@Bean
	public Queue queueEmail() {
		return new Queue(email,true);
	}
	
}
