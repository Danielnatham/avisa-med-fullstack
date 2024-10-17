import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './equipe-colaborador.reducer';

export const EquipeColaboradorDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const equipeColaboradorEntity = useAppSelector(state => state.equipeColaborador.entity);
  const updateSuccess = useAppSelector(state => state.equipeColaborador.updateSuccess);

  const handleClose = () => {
    navigate('/equipe-colaborador' + pageLocation.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(equipeColaboradorEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="equipeColaboradorDeleteDialogHeading">
        Confirme a exclusão
      </ModalHeader>
      <ModalBody id="avisaMedFullstackApp.equipeColaborador.delete.question">
        Tem certeza de que deseja excluir Equipe Colaborador {equipeColaboradorEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="jhi-confirm-delete-equipeColaborador" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Excluir
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default EquipeColaboradorDeleteDialog;
