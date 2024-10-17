package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.EquipeColaborador;
import br.ufs.avisamed.repository.EquipeColaboradorRepository;
import br.ufs.avisamed.service.EquipeColaboradorService;
import br.ufs.avisamed.service.dto.EquipeColaboradorDTO;
import br.ufs.avisamed.service.mapper.EquipeColaboradorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.ufs.avisamed.domain.EquipeColaborador}.
 */
@Service
@Transactional
public class EquipeColaboradorServiceImpl implements EquipeColaboradorService {

    private static final Logger log = LoggerFactory.getLogger(EquipeColaboradorServiceImpl.class);

    private final EquipeColaboradorRepository equipeColaboradorRepository;

    private final EquipeColaboradorMapper equipeColaboradorMapper;

    public EquipeColaboradorServiceImpl(
        EquipeColaboradorRepository equipeColaboradorRepository,
        EquipeColaboradorMapper equipeColaboradorMapper
    ) {
        this.equipeColaboradorRepository = equipeColaboradorRepository;
        this.equipeColaboradorMapper = equipeColaboradorMapper;
    }

    @Override
    public EquipeColaboradorDTO save(EquipeColaboradorDTO equipeColaboradorDTO) {
        log.debug("Request to save EquipeColaborador : {}", equipeColaboradorDTO);
        EquipeColaborador equipeColaborador = equipeColaboradorMapper.toEntity(equipeColaboradorDTO);
        equipeColaborador = equipeColaboradorRepository.save(equipeColaborador);
        return equipeColaboradorMapper.toDto(equipeColaborador);
    }

    @Override
    public EquipeColaboradorDTO update(EquipeColaboradorDTO equipeColaboradorDTO) {
        log.debug("Request to update EquipeColaborador : {}", equipeColaboradorDTO);
        EquipeColaborador equipeColaborador = equipeColaboradorMapper.toEntity(equipeColaboradorDTO);
        equipeColaborador = equipeColaboradorRepository.save(equipeColaborador);
        return equipeColaboradorMapper.toDto(equipeColaborador);
    }

    @Override
    public Optional<EquipeColaboradorDTO> partialUpdate(EquipeColaboradorDTO equipeColaboradorDTO) {
        log.debug("Request to partially update EquipeColaborador : {}", equipeColaboradorDTO);

        return equipeColaboradorRepository
            .findById(equipeColaboradorDTO.getId())
            .map(existingEquipeColaborador -> {
                equipeColaboradorMapper.partialUpdate(existingEquipeColaborador, equipeColaboradorDTO);

                return existingEquipeColaborador;
            })
            .map(equipeColaboradorRepository::save)
            .map(equipeColaboradorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeColaboradorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EquipeColaboradors");
        return equipeColaboradorRepository.findAll(pageable).map(equipeColaboradorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipeColaboradorDTO> findOne(Long id) {
        log.debug("Request to get EquipeColaborador : {}", id);
        return equipeColaboradorRepository.findById(id).map(equipeColaboradorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EquipeColaborador : {}", id);
        equipeColaboradorRepository.deleteById(id);
    }
}
