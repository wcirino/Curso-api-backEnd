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
import com.curso.dto.EmailDTO;
import com.curso.dto.InscricaoDTO;
import com.curso.dto.InscricaoDetalheDTO;
import com.curso.dto.StatusPagamentoMQ;
import com.curso.entity.Curso;
import com.curso.entity.Inscricao;
import com.curso.mq.publisher.CancelarMqPublisher;
import com.curso.mq.publisher.EmailMqPublisher;
import com.curso.mq.publisher.InscricaoMqPublisher;
import com.curso.repository.InscricaoRepository;
import com.curso.repository.custom.InscricaoCustomRepository;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;
    
    @Autowired
    private InscricaoCustomRepository inscricaoCustomRepository;
    
    @Autowired
    private EmailMqPublisher emailMqPublisher;
    
    @Autowired
    private CancelarMqPublisher cancelarMqpublisher;
    
    @Autowired
    private InscricaoMqPublisher mqInscricao;

    @Autowired
    private ModelMapper modelMapper;

    public InscricaoDTO inscreverAlunoCurso(InscricaoDTO inscricaoDTO) throws Exception {
        Inscricao inscricao = modelMapper.map(inscricaoDTO, Inscricao.class);
        Inscricao novaInscricao = inscricaoRepository.save(inscricao);
        mqInscricao.envioInscricao(inscricaoDTO, "Willyan", "Curso de java");
        emailMqPublisher.envioEmail(this.dtoEmail("Estudo rabbitMQ", "Willyan Cirino", novaInscricao));
        return modelMapper.map(novaInscricao, InscricaoDTO.class);
    }

    public void cancelarInscricao(Long id) throws Exception {
        if (inscricaoRepository.existsById(id)) {
            inscricaoRepository.deleteById(id);
            cancelarMqpublisher.envioCancelamentoInscricao(id, "Willyan Fernando", "Curso de exemplo");
            emailMqPublisher.envioEmail(this.dto_Email("Curso do projeto", "Willyan Cirino"));
            
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
    
    public Page<InscricaoDetalheDTO> listarInscricoesCurso(Pageable pageable) {
        return inscricaoCustomRepository.listarInscricoes(pageable);
    }

    public Page<CursoAlunoDTO> listarCursosDoAluno(Long alunoId, String nomeAluno, Pageable pageable) {
        return inscricaoCustomRepository.listarCursosDoAluno(alunoId, nomeAluno, pageable)
                .map(curso -> modelMapper.map(curso, CursoAlunoDTO.class));
    }

    public Page<AlunoPorCursoDTO> listarAlunosPorCurso(Long cursoId, String tituloCurso, Pageable pageable) {
        return inscricaoCustomRepository.listarAlunosPorCurso(cursoId, tituloCurso, pageable)
                .map(aluno -> modelMapper.map(aluno, AlunoPorCursoDTO.class));
    }
    
    public EmailDTO dtoEmail(String tituloCurso,String aluno,Inscricao insc) {
        
    	return EmailDTO.builder()
    			.assunto("Inscrição no curso")
    			.corpo("Curso :"+tituloCurso+ "Foi Inscrito com sucesso \n : aluno"+aluno+ "dados insc: "+ insc.toString())
    			.emailRemetente("email@email.com.br")
    			.titulo(tituloCurso)
    			.nome("Curso atualizando")
    			.build();
    }
    
	public EmailDTO dto_Email(String tituloCurso, String aluno) {

		return EmailDTO.builder().assunto("Deletar aluno do curso")
				.corpo("Curso :" + tituloCurso + " : aluno" + aluno + "dados insc: ")
				.emailRemetente("email@email.com.br").titulo(tituloCurso).nome("Curso delete").build();
	}

	public void atualizarStatusPagamento(StatusPagamentoMQ statusPagamento) {

		Long inscricaoId = statusPagamento.getId().longValue();
		Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
				.orElseThrow(() -> new RuntimeException("Inscrição não encontrada para o ID: " + inscricaoId));

		inscricao.setStatusPagamentoID(statusPagamento.getStatusPagamentoId());
		inscricao.setMetodoPagamentoID(statusPagamento.getMetodoPagamentoId());


		inscricaoRepository.save(inscricao);
	}
	
	public InscricaoDetalheDTO buscarInscricaoPorId(Long inscricaoId) {
		return inscricaoCustomRepository.buscarInscricaoPorId(inscricaoId);
	}

}

