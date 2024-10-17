import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IColaborador } from 'app/shared/model/colaborador.model';
import { getEntities as getColaboradors } from 'app/entities/colaborador/colaborador.reducer';
import { IGestorDepartamento } from 'app/shared/model/gestor-departamento.model';
import { getEntity, updateEntity, createEntity, reset } from './gestor-departamento.reducer';

export const GestorDepartamentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const colaboradors = useAppSelector(state => state.colaborador.entities);
  const gestorDepartamentoEntity = useAppSelector(state => state.gestorDepartamento.entity);
  const loading = useAppSelector(state => state.gestorDepartamento.loading);
  const updating = useAppSelector(state => state.gestorDepartamento.updating);
  const updateSuccess = useAppSelector(state => state.gestorDepartamento.updateSuccess);

  const handleClose = () => {
    navigate('/gestor-departamento' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getColaboradors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...gestorDepartamentoEntity,
      ...values,
      colaborador: colaboradors.find(it => it.id.toString() === values.colaborador?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...gestorDepartamentoEntity,
          colaborador: gestorDepartamentoEntity?.colaborador?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.gestorDepartamento.home.createOrEditLabel" data-cy="GestorDepartamentoCreateUpdateHeading">
            Criar ou editar Gestor Departamento
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="gestor-departamento-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Titulo"
                id="gestor-departamento-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Descricao"
                id="gestor-departamento-descricao"
                name="descricao"
                data-cy="descricao"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                id="gestor-departamento-colaborador"
                name="colaborador"
                data-cy="colaborador"
                label="Colaborador"
                type="select"
              >
                <option value="" key="0" />
                {colaboradors
                  ? colaboradors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gestor-departamento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Voltar</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Salvar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default GestorDepartamentoUpdate;
