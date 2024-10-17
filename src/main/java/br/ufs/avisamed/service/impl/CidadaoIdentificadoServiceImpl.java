package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.CidadaoIdentificado;
import br.ufs.avisamed.repository.CidadaoIdentificadoRepository;
import br.ufs.avisamed.service.CidadaoIdentificadoService;
import br.ufs.avisamed.service.dto.CidadaoIdentificadoDTO;
import br.ufs.avisamed.service.mapper.CidadaoIdentificadoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.ufs.avisamed.domain.CidadaoIdentificado}.
 */
@Service
@Transactional
public class CidadaoIdentificadoServiceImpl implements CidadaoIdentificadoService {

    private static final Logger log = LoggerFactory.getLogger(CidadaoIdentificadoServiceImpl.class);

    private final CidadaoIdentificadoRepository cidadaoIdentificadoRepository;

    private final CidadaoIdentificadoMapper cidadaoIdentificadoMapper;

    public CidadaoIdentificadoServiceImpl(
        CidadaoIdentificadoRepository cidadaoIdentificadoRepository,
        CidadaoIdentificadoMapper cidadaoIdentificadoMapper
    ) {
        this.cidadaoIdentificadoRepository = cidadaoIdentificadoRepository;
        this.cidadaoIdentificadoMapper = cidadaoIdentificadoMapper;
    }

    @Override
    public CidadaoIdentificadoDTO save(CidadaoIdentificadoDTO cidadaoIdentificadoDTO) {
        log.debug("Request to save CidadaoIdentificado : {}", cidadaoIdentificadoDTO);
        CidadaoIdentificado cidadaoIdentificado = cidadaoIdentificadoMapper.toEntity(cidadaoIdentificadoDTO);
        cidadaoIdentificado = cidadaoIdentificadoRepository.save(cidadaoIdentificado);
        return cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);
    }

    @Override
    public CidadaoIdentificadoDTO update(CidadaoIdentificadoDTO cidadaoIdentificadoDTO) {
        log.debug("Request to update CidadaoIdentificado : {}", cidadaoIdentificadoDTO);
        CidadaoIdentificado cidadaoIdentificado = cidadaoIdentificadoMapper.toEntity(cidadaoIdentificadoDTO);
        cidadaoIdentificado = cidadaoIdentificadoRepository.save(cidadaoIdentificado);
        return cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);
    }

    @Override
    public Optional<CidadaoIdentificadoDTO> partialUpdate(CidadaoIdentificadoDTO cidadaoIdentificadoDTO) {
        log.debug("Request to partially update CidadaoIdentificado : {}", cidadaoIdentificadoDTO);

        return cidadaoIdentificadoRepository
            .findById(cidadaoIdentificadoDTO.getId())
            .map(existingCidadaoIdentificado -> {
                cidadaoIdentificadoMapper.partialUpdate(existingCidadaoIdentificado, cidadaoIdentificadoDTO);

                return existingCidadaoIdentificado;
            })
            .map(cidadaoIdentificadoRepository::save)
            .map(cidadaoIdentificadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidadaoIdentificadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CidadaoIdentificados");
        return cidadaoIdentificadoRepository.findAll(pageable).map(cidadaoIdentificadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CidadaoIdentificadoDTO> findOne(Long id) {
        log.debug("Request to get CidadaoIdentificado : {}", id);
        return cidadaoIdentificadoRepository.findById(id).map(cidadaoIdentificadoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CidadaoIdentificado : {}", id);
        cidadaoIdentificadoRepository.deleteById(id);
    }
}
