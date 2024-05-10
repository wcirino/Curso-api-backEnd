package com.curso.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {

    private String nome;
    
    private String titulo;
    
    private String assunto;
    
    private String corpo;

    private String emailRemetente;
	
}
