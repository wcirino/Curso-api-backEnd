package com.curso.mq.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.curso.config.MQConfig;
import com.curso.dto.InscricaoDTO;
import com.curso.dto.inscricaoMQ;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InscricaoMqPublisher {
	
	private final RabbitTemplate rabbitTemplate;
	
	@Autowired
	private MQConfig mqserve;
	
	private static final Logger LOG = LoggerFactory.getLogger(InscricaoMqPublisher.class);
	
	public void envioInscricao(InscricaoDTO inscricao,String aluno,String titulo)  throws Exception{
		inscricaoMQ inscri = this.mapperInscricao(inscricao,aluno,titulo); 
		String json = this.convertIntoJson(inscri);
		LOG.info("Enviando dados inscrição !!");
		LOG.info(mqserve.queueCursoInscricaoAlunoltaFila().toString());
		LOG.info(json);
		rabbitTemplate.convertAndSend(mqserve.queueCursoInscricaoAlunoltaFila().getName(),json);
		LOG.info("Enviado inscrição !!!");
	}
	
	public  String convertIntoJson(inscricaoMQ inscricao) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json  = mapper.writeValueAsString(inscricao);
		return json;
	}
	
	public inscricaoMQ mapperInscricao(InscricaoDTO dto,String aluno,String titulo) {
		return inscricaoMQ
				.builder()
				.alunoId(dto.getAlunoID())
				.cursoId(dto.getAlunoID())
				.id(dto.getId())
				.titulo_curso(aluno)
				.nome_aluno(aluno)
				.statusPagamentoId(dto.getStatusPagamentoID())
				.metodoPagamentoId(dto.getMetodoPagamentoID())
				.build();
	}
}
