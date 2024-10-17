package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.Ocorrencia;
import br.ufs.avisamed.repository.OcorrenciaRepository;
import br.ufs.avisamed.service.OcorrenciaService;
import br.ufs.avisamed.service.dto.OcorrenciaDTO;
import br.ufs.avisamed.service.mapper.OcorrenciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.ufs.avisamed.domain.Ocorrencia}.
 */
@Service
@Transactional
public class OcorrenciaServiceImpl implements OcorrenciaService {

    private static final Logger log = LoggerFactory.getLogger(OcorrenciaServiceImpl.class);

    private final OcorrenciaRepository ocorrenciaRepository;

    private final OcorrenciaMapper ocorrenciaMapper;

    public OcorrenciaServiceImpl(OcorrenciaRepository ocorrenciaRepository, OcorrenciaMapper ocorrenciaMapper) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ocorrenciaMapper = ocorrenciaMapper;
    }

    @Override
    public OcorrenciaDTO save(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to save Ocorrencia : {}", ocorrenciaDTO);
        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(ocorrenciaDTO);
        ocorrencia = ocorrenciaRepository.save(ocorrencia);
        return ocorrenciaMapper.toDto(ocorrencia);
    }

    @Override
    public OcorrenciaDTO update(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to update Ocorrencia : {}", ocorrenciaDTO);
        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(ocorrenciaDTO);
        ocorrencia = ocorrenciaRepository.save(ocorrencia);
        return ocorrenciaMapper.toDto(ocorrencia);
    }

    @Override
    public Optional<OcorrenciaDTO> partialUpdate(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to partially update Ocorrencia : {}", ocorrenciaDTO);

        return ocorrenciaRepository
            .findById(ocorrenciaDTO.getId())
            .map(existingOcorrencia -> {
                ocorrenciaMapper.partialUpdate(existingOcorrencia, ocorrenciaDTO);

                return existingOcorrencia;
            })
            .map(ocorrenciaRepository::save)
            .map(ocorrenciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OcorrenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ocorrencias");
        return ocorrenciaRepository.findAll(pageable).map(ocorrenciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OcorrenciaDTO> findOne(Long id) {
        log.debug("Request to get Ocorrencia : {}", id);
        return ocorrenciaRepository.findById(id).map(ocorrenciaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ocorrencia : {}", id);
        ocorrenciaRepository.deleteById(id);
    }
}
