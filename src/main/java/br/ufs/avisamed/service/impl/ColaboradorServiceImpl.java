package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.Colaborador;
import br.ufs.avisamed.repository.ColaboradorRepository;
import br.ufs.avisamed.service.ColaboradorService;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
import br.ufs.avisamed.service.mapper.ColaboradorMapper;
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
 * Service Implementation for managing {@link br.ufs.avisamed.domain.Colaborador}.
 */
@Service
@Transactional
public class ColaboradorServiceImpl implements ColaboradorService {

    private static final Logger log = LoggerFactory.getLogger(ColaboradorServiceImpl.class);

    private final ColaboradorRepository colaboradorRepository;

    private final ColaboradorMapper colaboradorMapper;

    public ColaboradorServiceImpl(ColaboradorRepository colaboradorRepository, ColaboradorMapper colaboradorMapper) {
        this.colaboradorRepository = colaboradorRepository;
        this.colaboradorMapper = colaboradorMapper;
    }

    @Override
    public ColaboradorDTO save(ColaboradorDTO colaboradorDTO) {
        log.debug("Request to save Colaborador : {}", colaboradorDTO);
        Colaborador colaborador = colaboradorMapper.toEntity(colaboradorDTO);
        colaborador = colaboradorRepository.save(colaborador);
        return colaboradorMapper.toDto(colaborador);
    }

    @Override
    public ColaboradorDTO update(ColaboradorDTO colaboradorDTO) {
        log.debug("Request to update Colaborador : {}", colaboradorDTO);
        Colaborador colaborador = colaboradorMapper.toEntity(colaboradorDTO);
        colaborador = colaboradorRepository.save(colaborador);
        return colaboradorMapper.toDto(colaborador);
    }

    @Override
    public Optional<ColaboradorDTO> partialUpdate(ColaboradorDTO colaboradorDTO) {
        log.debug("Request to partially update Colaborador : {}", colaboradorDTO);

        return colaboradorRepository
            .findById(colaboradorDTO.getId())
            .map(existingColaborador -> {
                colaboradorMapper.partialUpdate(existingColaborador, colaboradorDTO);

                return existingColaborador;
            })
            .map(colaboradorRepository::save)
            .map(colaboradorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColaboradorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Colaboradors");
        return colaboradorRepository.findAll(pageable).map(colaboradorMapper::toDto);
    }

    /**
     *  Get all the colaboradors where GestorDepartamento is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ColaboradorDTO> findAllWhereGestorDepartamentoIsNull() {
        log.debug("Request to get all colaboradors where GestorDepartamento is null");
        return StreamSupport.stream(colaboradorRepository.findAll().spliterator(), false)
            .filter(colaborador -> colaborador.getGestorDepartamento() == null)
            .map(colaboradorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the colaboradors where GestorHu is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ColaboradorDTO> findAllWhereGestorHuIsNull() {
        log.debug("Request to get all colaboradors where GestorHu is null");
        return StreamSupport.stream(colaboradorRepository.findAll().spliterator(), false)
            .filter(colaborador -> colaborador.getGestorHu() == null)
            .map(colaboradorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the colaboradors where EquipeColaborador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ColaboradorDTO> findAllWhereEquipeColaboradorIsNull() {
        log.debug("Request to get all colaboradors where EquipeColaborador is null");
        return StreamSupport.stream(colaboradorRepository.findAll().spliterator(), false)
            .filter(colaborador -> colaborador.getEquipeColaborador() == null)
            .map(colaboradorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ColaboradorDTO> findOne(Long id) {
        log.debug("Request to get Colaborador : {}", id);
        return colaboradorRepository.findById(id).map(colaboradorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Colaborador : {}", id);
        colaboradorRepository.deleteById(id);
    }
}
