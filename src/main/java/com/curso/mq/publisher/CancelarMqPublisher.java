package com.curso.mq.publisher;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.curso.config.MQConfig;
import com.curso.dto.Cancelamento_AlunoMQ;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CancelarMqPublisher {

private final RabbitTemplate rabbitTemplate;
	
	@Autowired
	private MQConfig mqserve;
	
	private static final Logger LOG = LoggerFactory.getLogger(CancelarMqPublisher.class);
	
	public void envioCancelamentoInscricao(Long id,String nome, String titulo)  throws Exception{
		Cancelamento_AlunoMQ canc = this.mapperCancelarInscricao(id,nome,titulo); 
		String json = this.convertIntoJson(canc);
		LOG.info("Enviando Cancelar !!");
		LOG.info(mqserve.queueCursoCancelamentoAluno().toString());
		LOG.info(json);
		rabbitTemplate.convertAndSend(mqserve.queueCursoCancelamentoAluno().getName(),json);
		LOG.info("Enviado Cancelar !!!");
	}
	
	public  String convertIntoJson(Cancelamento_AlunoMQ canc) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json  = mapper.writeValueAsString(canc);
		return json;
	}
	
	public Cancelamento_AlunoMQ mapperCancelarInscricao(long id,String nome, String titulo) {
		return Cancelamento_AlunoMQ
				.builder()
				.curso_titulo(titulo)
				.nomeAluno(nome)
				.idInscricao(id)
				.dataalteracao(new Date())
				.build();
	}		
}
