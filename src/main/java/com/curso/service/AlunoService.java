package com.curso.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.dto.AlunoDTO;
import com.curso.entity.Aluno;
import com.curso.repository.AlunoRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AlunoDTO atualizarAluno(Long id, AlunoDTO alunoDTO) {
        if (alunoRepository.existsById(id)) {
            Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
            aluno.setId(id);
            Aluno alunoAtualizado = alunoRepository.save(aluno);
            return modelMapper.map(alunoAtualizado, AlunoDTO.class);
        } else {
            throw new RuntimeException("Aluno não encontrado para o ID: " + id);
        }
    }

    public List<AlunoDTO> listarAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .collect(Collectors.toList());
    }

    public AlunoDTO obterDetalhesAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o ID: " + id));
        return modelMapper.map(aluno, AlunoDTO.class);
    }
}

