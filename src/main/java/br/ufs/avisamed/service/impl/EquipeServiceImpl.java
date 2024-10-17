package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.Equipe;
import br.ufs.avisamed.repository.EquipeRepository;
import br.ufs.avisamed.service.EquipeService;
import br.ufs.avisamed.service.dto.EquipeDTO;
import br.ufs.avisamed.service.mapper.EquipeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.ufs.avisamed.domain.Equipe}.
 */
@Service
@Transactional
public class EquipeServiceImpl implements EquipeService {

    private static final Logger log = LoggerFactory.getLogger(EquipeServiceImpl.class);

    private final EquipeRepository equipeRepository;

    private final EquipeMapper equipeMapper;

    public EquipeServiceImpl(EquipeRepository equipeRepository, EquipeMapper equipeMapper) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
    }

    @Override
    public EquipeDTO save(EquipeDTO equipeDTO) {
        log.debug("Request to save Equipe : {}", equipeDTO);
        Equipe equipe = equipeMapper.toEntity(equipeDTO);
        equipe = equipeRepository.save(equipe);
        return equipeMapper.toDto(equipe);
    }

    @Override
    public EquipeDTO update(EquipeDTO equipeDTO) {
        log.debug("Request to update Equipe : {}", equipeDTO);
        Equipe equipe = equipeMapper.toEntity(equipeDTO);
        equipe = equipeRepository.save(equipe);
        return equipeMapper.toDto(equipe);
    }

    @Override
    public Optional<EquipeDTO> partialUpdate(EquipeDTO equipeDTO) {
        log.debug("Request to partially update Equipe : {}", equipeDTO);

        return equipeRepository
            .findById(equipeDTO.getId())
            .map(existingEquipe -> {
                equipeMapper.partialUpdate(existingEquipe, equipeDTO);

                return existingEquipe;
            })
            .map(equipeRepository::save)
            .map(equipeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAll(pageable).map(equipeMapper::toDto);
    }

    /**
     *  Get all the equipes where EquipeColaborador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EquipeDTO> findAllWhereEquipeColaboradorIsNull() {
        log.debug("Request to get all equipes where EquipeColaborador is null");
        return StreamSupport.stream(equipeRepository.findAll().spliterator(), false)
            .filter(equipe -> equipe.getEquipeColaborador() == null)
            .map(equipeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EquipeDTO> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findById(id).map(equipeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
    }
}
