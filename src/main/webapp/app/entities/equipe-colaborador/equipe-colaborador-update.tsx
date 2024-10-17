import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEquipe } from 'app/shared/model/equipe.model';
import { getEntities as getEquipes } from 'app/entities/equipe/equipe.reducer';
import { IColaborador } from 'app/shared/model/colaborador.model';
import { getEntities as getColaboradors } from 'app/entities/colaborador/colaborador.reducer';
import { IEquipeColaborador } from 'app/shared/model/equipe-colaborador.model';
import { getEntity, updateEntity, createEntity, reset } from './equipe-colaborador.reducer';

export const EquipeColaboradorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipes = useAppSelector(state => state.equipe.entities);
  const colaboradors = useAppSelector(state => state.colaborador.entities);
  const equipeColaboradorEntity = useAppSelector(state => state.equipeColaborador.entity);
  const loading = useAppSelector(state => state.equipeColaborador.loading);
  const updating = useAppSelector(state => state.equipeColaborador.updating);
  const updateSuccess = useAppSelector(state => state.equipeColaborador.updateSuccess);

  const handleClose = () => {
    navigate('/equipe-colaborador' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEquipes({}));
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
    if (values.idEquipe !== undefined && typeof values.idEquipe !== 'number') {
      values.idEquipe = Number(values.idEquipe);
    }

    const entity = {
      ...equipeColaboradorEntity,
      ...values,
      equipe: equipes.find(it => it.id.toString() === values.equipe?.toString()),
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
          ...equipeColaboradorEntity,
          equipe: equipeColaboradorEntity?.equipe?.id,
          colaborador: equipeColaboradorEntity?.colaborador?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.equipeColaborador.home.createOrEditLabel" data-cy="EquipeColaboradorCreateUpdateHeading">
            Criar ou editar Equipe Colaborador
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
                <ValidatedField name="id" required readOnly id="equipe-colaborador-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Id Colaborador"
                id="equipe-colaborador-idColaborador"
                name="idColaborador"
                data-cy="idColaborador"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Id Equipe"
                id="equipe-colaborador-idEquipe"
                name="idEquipe"
                data-cy="idEquipe"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField id="equipe-colaborador-equipe" name="equipe" data-cy="equipe" label="Equipe" type="select">
                <option value="" key="0" />
                {equipes
                  ? equipes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="equipe-colaborador-colaborador"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipe-colaborador" replace color="info">
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

export default EquipeColaboradorUpdate;
