package com.curso.mq.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.curso.config.MQConfig;
import com.curso.dto.EmailDTO;
import com.curso.dto.EmailMQ;
import com.curso.dto.InscricaoDTO;
import com.curso.dto.inscricaoMQ;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailMqPublisher {

	private final RabbitTemplate rabbitTemplate;
	
	@Autowired
	private MQConfig mqserve;
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailMqPublisher.class);
	
	public void envioEmail(EmailDTO email)  throws Exception{
		EmailMQ eml = this.mapperInscricao(email); 
		String json = this.convertIntoJson(eml);
		LOG.info("Enviando cancelar !!");
		LOG.info(mqserve.queueCursoInscricaoAlunoltaFila().toString());
		LOG.info(json);
		rabbitTemplate.convertAndSend(mqserve.queueEmail().getName(),json);
		LOG.info("Enviado Email !!!");
	}
	
	public  String convertIntoJson(EmailMQ email) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json  = mapper.writeValueAsString(email);
		return json;
	}
	
	public EmailMQ mapperInscricao(EmailDTO dto) {
		return EmailMQ
				.builder()
				.emailRemetente(dto.getEmailRemetente())
				.assunto(dto.getAssunto())
				.corpo(dto.getCorpo())
				.titulo(dto.getTitulo())
				.nome(dto.getNome())
				.build();
	}	
	
}
