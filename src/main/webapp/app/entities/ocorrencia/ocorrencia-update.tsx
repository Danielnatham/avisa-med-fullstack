import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICidadaoIdentificado } from 'app/shared/model/cidadao-identificado.model';
import { getEntities as getCidadaoIdentificados } from 'app/entities/cidadao-identificado/cidadao-identificado.reducer';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { getEntities as getDepartamentos } from 'app/entities/departamento/departamento.reducer';
import { IEquipe } from 'app/shared/model/equipe.model';
import { getEntities as getEquipes } from 'app/entities/equipe/equipe.reducer';
import { IOcorrencia } from 'app/shared/model/ocorrencia.model';
import { getEntity, updateEntity, createEntity, reset } from './ocorrencia.reducer';

export const OcorrenciaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cidadaoIdentificados = useAppSelector(state => state.cidadaoIdentificado.entities);
  const departamentos = useAppSelector(state => state.departamento.entities);
  const equipes = useAppSelector(state => state.equipe.entities);
  const ocorrenciaEntity = useAppSelector(state => state.ocorrencia.entity);
  const loading = useAppSelector(state => state.ocorrencia.loading);
  const updating = useAppSelector(state => state.ocorrencia.updating);
  const updateSuccess = useAppSelector(state => state.ocorrencia.updateSuccess);

  const handleClose = () => {
    navigate('/ocorrencia' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCidadaoIdentificados({}));
    dispatch(getDepartamentos({}));
    dispatch(getEquipes({}));
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
    if (values.idSolicitante !== undefined && typeof values.idSolicitante !== 'number') {
      values.idSolicitante = Number(values.idSolicitante);
    }
    if (values.idDepartamento !== undefined && typeof values.idDepartamento !== 'number') {
      values.idDepartamento = Number(values.idDepartamento);
    }
    values.dataCriacao = convertDateTimeToServer(values.dataCriacao);
    values.dataResolucao = convertDateTimeToServer(values.dataResolucao);
    if (values.complexidade !== undefined && typeof values.complexidade !== 'number') {
      values.complexidade = Number(values.complexidade);
    }

    const entity = {
      ...ocorrenciaEntity,
      ...values,
      cidadaoIdentificado: cidadaoIdentificados.find(it => it.id.toString() === values.cidadaoIdentificado?.toString()),
      departamento: departamentos.find(it => it.id.toString() === values.departamento?.toString()),
      equipe: equipes.find(it => it.id.toString() === values.equipe?.toString()),
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
          dataCriacao: displayDefaultDateTime(),
          dataResolucao: displayDefaultDateTime(),
        }
      : {
          ...ocorrenciaEntity,
          dataCriacao: convertDateTimeFromServer(ocorrenciaEntity.dataCriacao),
          dataResolucao: convertDateTimeFromServer(ocorrenciaEntity.dataResolucao),
          cidadaoIdentificado: ocorrenciaEntity?.cidadaoIdentificado?.id,
          departamento: ocorrenciaEntity?.departamento?.id,
          equipe: ocorrenciaEntity?.equipe?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.ocorrencia.home.createOrEditLabel" data-cy="OcorrenciaCreateUpdateHeading">
            Criar ou editar Ocorrencia
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
                <ValidatedField name="id" required readOnly id="ocorrencia-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Id Solicitante"
                id="ocorrencia-idSolicitante"
                name="idSolicitante"
                data-cy="idSolicitante"
                type="text"
              />
              <ValidatedField
                label="Id Departamento"
                id="ocorrencia-idDepartamento"
                name="idDepartamento"
                data-cy="idDepartamento"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Data Criacao"
                id="ocorrencia-dataCriacao"
                name="dataCriacao"
                data-cy="dataCriacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Data Resolucao"
                id="ocorrencia-dataResolucao"
                name="dataResolucao"
                data-cy="dataResolucao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Titulo"
                id="ocorrencia-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 50, message: 'Este campo não pode ter mais de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Descricao"
                id="ocorrencia-descricao"
                name="descricao"
                data-cy="descricao"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 200, message: 'Este campo não pode ter mais de 200 caracteres.' },
                }}
              />
              <ValidatedField
                label="Imagem"
                id="ocorrencia-imagem"
                name="imagem"
                data-cy="imagem"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 100, message: 'Este campo não pode ter mais de 100 caracteres.' },
                }}
              />
              <ValidatedField
                label="Situacao"
                id="ocorrencia-situacao"
                name="situacao"
                data-cy="situacao"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 10, message: 'Este campo não pode ter mais de 10 caracteres.' },
                }}
              />
              <ValidatedField
                label="Complexidade"
                id="ocorrencia-complexidade"
                name="complexidade"
                data-cy="complexidade"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  min: { value: 1, message: 'Este campo deve ser maior que 1.' },
                  max: { value: 10, message: 'Este campo não pode ser maior que 10.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Protocolo"
                id="ocorrencia-protocolo"
                name="protocolo"
                data-cy="protocolo"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 12, message: 'Este campo não pode ter mais de 12 caracteres.' },
                }}
              />
              <ValidatedField
                id="ocorrencia-cidadaoIdentificado"
                name="cidadaoIdentificado"
                data-cy="cidadaoIdentificado"
                label="Cidadao Identificado"
                type="select"
              >
                <option value="" key="0" />
                {cidadaoIdentificados
                  ? cidadaoIdentificados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="ocorrencia-departamento" name="departamento" data-cy="departamento" label="Departamento" type="select">
                <option value="" key="0" />
                {departamentos
                  ? departamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="ocorrencia-equipe" name="equipe" data-cy="equipe" label="Equipe" type="select">
                <option value="" key="0" />
                {equipes
                  ? equipes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ocorrencia" replace color="info">
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

export default OcorrenciaUpdate;
