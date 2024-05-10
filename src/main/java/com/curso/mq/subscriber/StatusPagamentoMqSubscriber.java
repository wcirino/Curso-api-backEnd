package com.curso.mq.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.curso.dto.StatusPagamentoMQ;
import com.curso.service.InscricaoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class StatusPagamentoMqSubscriber {

private static final Logger LOG = LoggerFactory.getLogger(StatusPagamentoMqSubscriber.class);
	
	@Autowired
	private InscricaoService service;
	
	@RabbitListener(queues = "${mq.queues.status-pagamento}")
	public void recebendoStatusPagamento(@Payload String payload) throws Exception {
		LOG.info("recebendo dados pagamento!!");
		ObjectMapper mapper = new ObjectMapper();
		//ConsultaDTO dados = mapper.readValue(payload, ConsultaDTO.class);
		StatusPagamentoMQ dados = mapper.readValue(payload, StatusPagamentoMQ.class);
		LOG.info("Deu Certo!!");
		service.atualizarStatusPagamento(dados);
		LOG.info("Feita alteração do pagamento");
		LOG.info(dados.toString());
	}
		
}
