package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.GestorDepartamento;
import br.ufs.avisamed.repository.GestorDepartamentoRepository;
import br.ufs.avisamed.service.GestorDepartamentoService;
import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
import br.ufs.avisamed.service.mapper.GestorDepartamentoMapper;
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
 * Service Implementation for managing {@link br.ufs.avisamed.domain.GestorDepartamento}.
 */
@Service
@Transactional
public class GestorDepartamentoServiceImpl implements GestorDepartamentoService {

    private static final Logger log = LoggerFactory.getLogger(GestorDepartamentoServiceImpl.class);

    private final GestorDepartamentoRepository gestorDepartamentoRepository;

    private final GestorDepartamentoMapper gestorDepartamentoMapper;

    public GestorDepartamentoServiceImpl(
        GestorDepartamentoRepository gestorDepartamentoRepository,
        GestorDepartamentoMapper gestorDepartamentoMapper
    ) {
        this.gestorDepartamentoRepository = gestorDepartamentoRepository;
        this.gestorDepartamentoMapper = gestorDepartamentoMapper;
    }

    @Override
    public GestorDepartamentoDTO save(GestorDepartamentoDTO gestorDepartamentoDTO) {
        log.debug("Request to save GestorDepartamento : {}", gestorDepartamentoDTO);
        GestorDepartamento gestorDepartamento = gestorDepartamentoMapper.toEntity(gestorDepartamentoDTO);
        gestorDepartamento = gestorDepartamentoRepository.save(gestorDepartamento);
        return gestorDepartamentoMapper.toDto(gestorDepartamento);
    }

    @Override
    public GestorDepartamentoDTO update(GestorDepartamentoDTO gestorDepartamentoDTO) {
        log.debug("Request to update GestorDepartamento : {}", gestorDepartamentoDTO);
        GestorDepartamento gestorDepartamento = gestorDepartamentoMapper.toEntity(gestorDepartamentoDTO);
        gestorDepartamento = gestorDepartamentoRepository.save(gestorDepartamento);
        return gestorDepartamentoMapper.toDto(gestorDepartamento);
    }

    @Override
    public Optional<GestorDepartamentoDTO> partialUpdate(GestorDepartamentoDTO gestorDepartamentoDTO) {
        log.debug("Request to partially update GestorDepartamento : {}", gestorDepartamentoDTO);

        return gestorDepartamentoRepository
            .findById(gestorDepartamentoDTO.getId())
            .map(existingGestorDepartamento -> {
                gestorDepartamentoMapper.partialUpdate(existingGestorDepartamento, gestorDepartamentoDTO);

                return existingGestorDepartamento;
            })
            .map(gestorDepartamentoRepository::save)
            .map(gestorDepartamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GestorDepartamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GestorDepartamentos");
        return gestorDepartamentoRepository.findAll(pageable).map(gestorDepartamentoMapper::toDto);
    }

    /**
     *  Get all the gestorDepartamentos where Departamento is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GestorDepartamentoDTO> findAllWhereDepartamentoIsNull() {
        log.debug("Request to get all gestorDepartamentos where Departamento is null");
        return StreamSupport.stream(gestorDepartamentoRepository.findAll().spliterator(), false)
            .filter(gestorDepartamento -> gestorDepartamento.getDepartamento() == null)
            .map(gestorDepartamentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GestorDepartamentoDTO> findOne(Long id) {
        log.debug("Request to get GestorDepartamento : {}", id);
        return gestorDepartamentoRepository.findById(id).map(gestorDepartamentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GestorDepartamento : {}", id);
        gestorDepartamentoRepository.deleteById(id);
    }
}
