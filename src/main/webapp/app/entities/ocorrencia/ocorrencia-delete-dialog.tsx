import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './ocorrencia.reducer';

export const OcorrenciaDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const ocorrenciaEntity = useAppSelector(state => state.ocorrencia.entity);
  const updateSuccess = useAppSelector(state => state.ocorrencia.updateSuccess);

  const handleClose = () => {
    navigate('/ocorrencia' + pageLocation.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(ocorrenciaEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="ocorrenciaDeleteDialogHeading">
        Confirme a exclusão
      </ModalHeader>
      <ModalBody id="avisaMedFullstackApp.ocorrencia.delete.question">
        Tem certeza de que deseja excluir Ocorrencia {ocorrenciaEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="jhi-confirm-delete-ocorrencia" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Excluir
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default OcorrenciaDeleteDialog;
