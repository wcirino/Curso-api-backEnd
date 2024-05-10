package com.curso.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.dto.CursoDTO;
import com.curso.dto.EmailDTO;
import com.curso.entity.Curso;
import com.curso.mq.publisher.EmailMqPublisher;
import com.curso.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private EmailMqPublisher emailMqPublisher;

    @Autowired
    private ModelMapper modelMapper;

    public CursoDTO criarCurso(CursoDTO cursoDTO) {
        Curso curso = modelMapper.map(cursoDTO, Curso.class);
        Curso cursoSalvo = cursoRepository.save(curso);
        return modelMapper.map(cursoSalvo, CursoDTO.class);
    }

    public CursoDTO atualizarCurso(Long id, CursoDTO cursoDTO) throws Exception {
        if (cursoRepository.existsById(id)) {
            Curso curso = modelMapper.map(cursoDTO, Curso.class);
            curso.setId(id);
            Curso cursoAtualizado = cursoRepository.save(curso);
            emailMqPublisher.envioEmail(this.dtoEmail(cursoAtualizado.getTitulo(),cursoAtualizado));
            return modelMapper.map(cursoAtualizado, CursoDTO.class);
        } else {
            throw new RuntimeException("Curso não encontrado para o ID: " + id);
        }
    }

    public List<CursoDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(curso -> modelMapper.map(curso, CursoDTO.class))
                .collect(Collectors.toList());
    }

    public void excluirCurso(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Curso não encontrado para o ID: " + id);
        }
    }

    public CursoDTO obterDetalhesCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado para o ID: " + id));
        return modelMapper.map(curso, CursoDTO.class);
    }
    
    public EmailDTO dtoEmail(String tituloCurso,Curso curso) {
    
    	return EmailDTO.builder()
    			.assunto("Atualização de curso")
    			.corpo("Curso :"+tituloCurso+ "Foi atualizado com sucesso \n : "+ curso.toString())
    			.emailRemetente("email@email.com.br")
    			.titulo(tituloCurso)
    			.nome("Curso atualizando")
    			.build();
    }
}
