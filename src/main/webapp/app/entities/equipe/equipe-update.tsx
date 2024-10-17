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
import { IEquipe } from 'app/shared/model/equipe.model';
import { getEntity, updateEntity, createEntity, reset } from './equipe.reducer';

export const EquipeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gestorDepartamentos = useAppSelector(state => state.gestorDepartamento.entities);
  const equipeEntity = useAppSelector(state => state.equipe.entity);
  const loading = useAppSelector(state => state.equipe.loading);
  const updating = useAppSelector(state => state.equipe.updating);
  const updateSuccess = useAppSelector(state => state.equipe.updateSuccess);

  const handleClose = () => {
    navigate('/equipe' + location.search);
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
    if (values.idOcorrencia !== undefined && typeof values.idOcorrencia !== 'number') {
      values.idOcorrencia = Number(values.idOcorrencia);
    }
    if (values.idG !== undefined && typeof values.idG !== 'number') {
      values.idG = Number(values.idG);
    }
    values.dataAtribuicao = convertDateTimeToServer(values.dataAtribuicao);
    values.dataFinalizacao = convertDateTimeToServer(values.dataFinalizacao);

    const entity = {
      ...equipeEntity,
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
      ? {
          dataAtribuicao: displayDefaultDateTime(),
          dataFinalizacao: displayDefaultDateTime(),
        }
      : {
          ...equipeEntity,
          dataAtribuicao: convertDateTimeFromServer(equipeEntity.dataAtribuicao),
          dataFinalizacao: convertDateTimeFromServer(equipeEntity.dataFinalizacao),
          gestorDepartamento: equipeEntity?.gestorDepartamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.equipe.home.createOrEditLabel" data-cy="EquipeCreateUpdateHeading">
            Criar ou editar Equipe
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="equipe-id" label="Código" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Id Ocorrencia"
                id="equipe-idOcorrencia"
                name="idOcorrencia"
                data-cy="idOcorrencia"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Id G"
                id="equipe-idG"
                name="idG"
                data-cy="idG"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Data Atribuicao"
                id="equipe-dataAtribuicao"
                name="dataAtribuicao"
                data-cy="dataAtribuicao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Data Finalizacao"
                id="equipe-dataFinalizacao"
                name="dataFinalizacao"
                data-cy="dataFinalizacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                id="equipe-gestorDepartamento"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipe" replace color="info">
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

export default EquipeUpdate;
