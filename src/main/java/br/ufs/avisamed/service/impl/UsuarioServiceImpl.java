package br.ufs.avisamed.service.impl;

import br.ufs.avisamed.domain.Usuario;
import br.ufs.avisamed.repository.UsuarioRepository;
import br.ufs.avisamed.service.UsuarioService;
import br.ufs.avisamed.service.dto.UsuarioDTO;
import br.ufs.avisamed.service.mapper.UsuarioMapper;
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
 * Service Implementation for managing {@link br.ufs.avisamed.domain.Usuario}.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDTO update(UsuarioDTO usuarioDTO) {
        log.debug("Request to update Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public Optional<UsuarioDTO> partialUpdate(UsuarioDTO usuarioDTO) {
        log.debug("Request to partially update Usuario : {}", usuarioDTO);

        return usuarioRepository
            .findById(usuarioDTO.getId())
            .map(existingUsuario -> {
                usuarioMapper.partialUpdate(existingUsuario, usuarioDTO);

                return existingUsuario;
            })
            .map(usuarioRepository::save)
            .map(usuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toDto);
    }

    /**
     *  Get all the usuarios where Colaborador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllWhereColaboradorIsNull() {
        log.debug("Request to get all usuarios where Colaborador is null");
        return StreamSupport.stream(usuarioRepository.findAll().spliterator(), false)
            .filter(usuario -> usuario.getColaborador() == null)
            .map(usuarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the usuarios where CidadaoIdentificado is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllWhereCidadaoIdentificadoIsNull() {
        log.debug("Request to get all usuarios where CidadaoIdentificado is null");
        return StreamSupport.stream(usuarioRepository.findAll().spliterator(), false)
            .filter(usuario -> usuario.getCidadaoIdentificado() == null)
            .map(usuarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id).map(usuarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
