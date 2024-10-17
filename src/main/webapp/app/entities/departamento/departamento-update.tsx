import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGestorDepartamento } from 'app/shared/model/gestor-departamento.model';
import { getEntities as getGestorDepartamentos } from 'app/entities/gestor-departamento/gestor-departamento.reducer';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { getEntity, updateEntity, createEntity, reset } from './departamento.reducer';

export const DepartamentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gestorDepartamentos = useAppSelector(state => state.gestorDepartamento.entities);
  const departamentoEntity = useAppSelector(state => state.departamento.entity);
  const loading = useAppSelector(state => state.departamento.loading);
  const updating = useAppSelector(state => state.departamento.updating);
  const updateSuccess = useAppSelector(state => state.departamento.updateSuccess);

  const handleClose = () => {
    navigate('/departamento' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getGestorDepartamentos({}));
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
    if (values.idGestorDepartamento !== undefined && typeof values.idGestorDepartamento !== 'number') {
      values.idGestorDepartamento = Number(values.idGestorDepartamento);
    }

    const entity = {
      ...departamentoEntity,
      ...values,
      gestorDepartamento: gestorDepartamentos.find(it => it.id.toString() === values.gestorDepartamento?.toString()),
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
          ...departamentoEntity,
          gestorDepartamento: departamentoEntity?.gestorDepartamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.departamento.home.createOrEditLabel" data-cy="DepartamentoCreateUpdateHeading">
            Criar ou editar Departamento
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
                <ValidatedField name="id" required readOnly id="departamento-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Id Gestor Departamento"
                id="departamento-idGestorDepartamento"
                name="idGestorDepartamento"
                data-cy="idGestorDepartamento"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Nome"
                id="departamento-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 30, message: 'Este campo não pode ter mais de 30 caracteres.' },
                }}
              />
              <ValidatedField
                label="Descricao"
                id="departamento-descricao"
                name="descricao"
                data-cy="descricao"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 200, message: 'Este campo não pode ter mais de 200 caracteres.' },
                }}
              />
              <ValidatedField
                id="departamento-gestorDepartamento"
                name="gestorDepartamento"
                data-cy="gestorDepartamento"
                label="Gestor Departamento"
                type="select"
              >
                <option value="" key="0" />
                {gestorDepartamentos
                  ? gestorDepartamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/departamento" replace color="info">
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

export default DepartamentoUpdate;
