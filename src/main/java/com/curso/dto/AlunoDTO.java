package com.curso.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {

    private Long id;
    private String nome;
    private String email;
    private Date dataNascimento;
    private Integer genero;
    private String telefone;
    private Long numMatricula;

}
