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
import { IGestorHu } from 'app/shared/model/gestor-hu.model';
import { getEntity, updateEntity, createEntity, reset } from './gestor-hu.reducer';

export const GestorHuUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const colaboradors = useAppSelector(state => state.colaborador.entities);
  const gestorHuEntity = useAppSelector(state => state.gestorHu.entity);
  const loading = useAppSelector(state => state.gestorHu.loading);
  const updating = useAppSelector(state => state.gestorHu.updating);
  const updateSuccess = useAppSelector(state => state.gestorHu.updateSuccess);

  const handleClose = () => {
    navigate('/gestor-hu' + location.search);
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
    if (values.idColaborador !== undefined && typeof values.idColaborador !== 'number') {
      values.idColaborador = Number(values.idColaborador);
    }

    const entity = {
      ...gestorHuEntity,
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
          ...gestorHuEntity,
          colaborador: gestorHuEntity?.colaborador?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.gestorHu.home.createOrEditLabel" data-cy="GestorHuCreateUpdateHeading">
            Criar ou editar Gestor Hu
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
                <ValidatedField name="id" required readOnly id="gestor-hu-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Id Colaborador"
                id="gestor-hu-idColaborador"
                name="idColaborador"
                data-cy="idColaborador"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField id="gestor-hu-colaborador" name="colaborador" data-cy="colaborador" label="Colaborador" type="select">
                <option value="" key="0" />
                {colaboradors
                  ? colaboradors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gestor-hu" replace color="info">
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

export default GestorHuUpdate;
