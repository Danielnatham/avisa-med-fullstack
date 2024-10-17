import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntities as getUsuarios } from 'app/entities/usuario/usuario.reducer';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { getEntities as getDepartamentos } from 'app/entities/departamento/departamento.reducer';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { getEntity, updateEntity, createEntity, reset } from './colaborador.reducer';

export const ColaboradorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const usuarios = useAppSelector(state => state.usuario.entities);
  const departamentos = useAppSelector(state => state.departamento.entities);
  const colaboradorEntity = useAppSelector(state => state.colaborador.entity);
  const loading = useAppSelector(state => state.colaborador.loading);
  const updating = useAppSelector(state => state.colaborador.updating);
  const updateSuccess = useAppSelector(state => state.colaborador.updateSuccess);

  const handleClose = () => {
    navigate('/colaborador' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsuarios({}));
    dispatch(getDepartamentos({}));
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
    if (values.idDepartamento !== undefined && typeof values.idDepartamento !== 'number') {
      values.idDepartamento = Number(values.idDepartamento);
    }
    if (values.idUsuario !== undefined && typeof values.idUsuario !== 'number') {
      values.idUsuario = Number(values.idUsuario);
    }

    const entity = {
      ...colaboradorEntity,
      ...values,
      usuario: usuarios.find(it => it.id.toString() === values.usuario?.toString()),
      departamento: departamentos.find(it => it.id.toString() === values.departamento?.toString()),
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
          ...colaboradorEntity,
          usuario: colaboradorEntity?.usuario?.id,
          departamento: colaboradorEntity?.departamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.colaborador.home.createOrEditLabel" data-cy="ColaboradorCreateUpdateHeading">
            Criar ou editar Colaborador
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
                <ValidatedField name="id" required readOnly id="colaborador-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Id Departamento"
                id="colaborador-idDepartamento"
                name="idDepartamento"
                data-cy="idDepartamento"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Id Usuario"
                id="colaborador-idUsuario"
                name="idUsuario"
                data-cy="idUsuario"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField id="colaborador-usuario" name="usuario" data-cy="usuario" label="Usuario" type="select">
                <option value="" key="0" />
                {usuarios
                  ? usuarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="colaborador-departamento" name="departamento" data-cy="departamento" label="Departamento" type="select">
                <option value="" key="0" />
                {departamentos
                  ? departamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/colaborador" replace color="info">
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

export default ColaboradorUpdate;
