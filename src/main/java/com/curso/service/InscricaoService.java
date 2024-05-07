package com.curso.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.curso.dto.AlunoPorCursoDTO;
import com.curso.dto.CursoAlunoDTO;
import com.curso.dto.InscricaoDTO;
import com.curso.entity.Inscricao;
import com.curso.repository.InscricaoRepository;
import com.curso.repository.custom.InscricaoCustomRepository;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;
    
    @Autowired
    private InscricaoCustomRepository inscricaoCustomRepository;

    @Autowired
    private ModelMapper modelMapper;

    public InscricaoDTO inscreverAlunoCurso(InscricaoDTO inscricaoDTO) {
        Inscricao inscricao = modelMapper.map(inscricaoDTO, Inscricao.class);
        Inscricao novaInscricao = inscricaoRepository.save(inscricao);
        return modelMapper.map(novaInscricao, InscricaoDTO.class);
    }

    public void cancelarInscricao(Long id) {
        if (inscricaoRepository.existsById(id)) {
            inscricaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Inscrição não encontrada para o ID: " + id);
        }
    }

    public List<InscricaoDTO> listarInscricoesPorCurso(Long cursoId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByCursoID(cursoId);
        return inscricoes.stream()
                .map(inscricao -> modelMapper.map(inscricao, InscricaoDTO.class))
                .collect(Collectors.toList());
    }

    public List<InscricaoDTO> listarInscricoesPorAluno(Long alunoId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoID(alunoId);
        return inscricoes.stream()
                .map(inscricao -> modelMapper.map(inscricao, InscricaoDTO.class))
                .collect(Collectors.toList());
    }
    
    public Page<InscricaoDTO> listarInscricoesCurso(Long cursoId, Pageable pageable) {
        return inscricaoCustomRepository.listarInscricoes(pageable)
                .map(inscricao -> modelMapper.map(inscricao, InscricaoDTO.class));
    }

    public Page<CursoAlunoDTO> listarCursosDoAluno(Long alunoId, Pageable pageable) {
        return inscricaoCustomRepository.listarCursosDoAluno(alunoId, pageable)
                .map(curso -> modelMapper.map(curso, CursoAlunoDTO.class));
    }

    public Page<AlunoPorCursoDTO> listarAlunosPorCurso(Long cursoId, Pageable pageable) {
        return inscricaoCustomRepository.listarAlunosPorCurso(cursoId, pageable)
                .map(aluno -> modelMapper.map(aluno, AlunoPorCursoDTO.class));
    }
}

