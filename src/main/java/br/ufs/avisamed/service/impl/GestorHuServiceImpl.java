package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.GestorHu;
import br.ufs.avisamed.repository.GestorHuRepository;
import br.ufs.avisamed.service.GestorHuService;
import br.ufs.avisamed.service.dto.GestorHuDTO;
import br.ufs.avisamed.service.mapper.GestorHuMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.ufs.avisamed.domain.GestorHu}.
 */
@Service
@Transactional
public class GestorHuServiceImpl implements GestorHuService {

    private static final Logger log = LoggerFactory.getLogger(GestorHuServiceImpl.class);

    private final GestorHuRepository gestorHuRepository;

    private final GestorHuMapper gestorHuMapper;

    public GestorHuServiceImpl(GestorHuRepository gestorHuRepository, GestorHuMapper gestorHuMapper) {
        this.gestorHuRepository = gestorHuRepository;
        this.gestorHuMapper = gestorHuMapper;
    }

    @Override
    public GestorHuDTO save(GestorHuDTO gestorHuDTO) {
        log.debug("Request to save GestorHu : {}", gestorHuDTO);
        GestorHu gestorHu = gestorHuMapper.toEntity(gestorHuDTO);
        gestorHu = gestorHuRepository.save(gestorHu);
        return gestorHuMapper.toDto(gestorHu);
    }

    @Override
    public GestorHuDTO update(GestorHuDTO gestorHuDTO) {
        log.debug("Request to update GestorHu : {}", gestorHuDTO);
        GestorHu gestorHu = gestorHuMapper.toEntity(gestorHuDTO);
        gestorHu = gestorHuRepository.save(gestorHu);
        return gestorHuMapper.toDto(gestorHu);
    }

    @Override
    public Optional<GestorHuDTO> partialUpdate(GestorHuDTO gestorHuDTO) {
        log.debug("Request to partially update GestorHu : {}", gestorHuDTO);

        return gestorHuRepository
            .findById(gestorHuDTO.getId())
            .map(existingGestorHu -> {
                gestorHuMapper.partialUpdate(existingGestorHu, gestorHuDTO);

                return existingGestorHu;
            })
            .map(gestorHuRepository::save)
            .map(gestorHuMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GestorHuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GestorHus");
        return gestorHuRepository.findAll(pageable).map(gestorHuMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GestorHuDTO> findOne(Long id) {
        log.debug("Request to get GestorHu : {}", id);
        return gestorHuRepository.findById(id).map(gestorHuMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GestorHu : {}", id);
        gestorHuRepository.deleteById(id);
    }
}
