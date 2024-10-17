import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCidadaoIdentificados } from 'app/entities/cidadao-identificado/cidadao-identificado.reducer';
import { getEntities as getDepartamentos } from 'app/entities/departamento/departamento.reducer';
import { getEntities as getEquipes } from 'app/entities/equipe/equipe.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ocorrencia.reducer';
import { ICidadaoIdentificado } from 'app/shared/model/cidadao-identificado.model';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { IEquipe } from 'app/shared/model/equipe.model';

export const OcorrenciaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cidadaoIdentificados: ICidadaoIdentificado[] = useAppSelector(state => state.cidadaoIdentificado.entities);
  const departamentos: IDepartamento[] = useAppSelector(state => state.departamento.entities);
  const equipes: IEquipe[] = useAppSelector(state => state.equipe.entities);
  const ocorrenciaEntity = useAppSelector(state => state.ocorrencia.entity);
  const loading = useAppSelector(state => state.ocorrencia.loading);
  const updating = useAppSelector(state => state.ocorrencia.updating);
  const updateSuccess = useAppSelector(state => state.ocorrencia.updateSuccess);

  const handleClose = () => {
    navigate('/ocorrencia' + location.search);
  };

  const complexidades = [
    { value: '1', label: 'Baixa' },
    { value: '5', label: 'Média' },
    { value: '10', label: 'Alta' },
  ];

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
          situacao: 'ATRIBUIDO',
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
                label="Data Criacao"
                id="ocorrencia-dataCriacao"
                name="dataCriacao"
                data-cy="dataCriacao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                defaultValue={displayDefaultDateTime()}
                hidden
              />
              <ValidatedField
                label="Data Resolucao"
                id="ocorrencia-dataResolucao"
                name="dataResolucao"
                data-cy="dataResolucao"
                defaultValue={displayDefaultDateTime()}
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                hidden
              />
              <ValidatedField label="Titulo" id="ocorrencia-titulo" name="titulo" data-cy="titulo" type="text" />
              <ValidatedField label="Descricao" id="ocorrencia-descricao" name="descricao" data-cy="descricao" type="text" />
              <ValidatedField label="Situacao" id="ocorrencia-situacao" name="situacao" data-cy="situacao" type="text" hidden={isNew} />
              {isNew && (
                <ValidatedField label="Complexidade" id="ocorrencia-complexidade" name="complexidade" data-cy="complexidade" type="select">
                  {complexidades
                    ? complexidades.map(otherEntity => (
                        <option value={otherEntity.value} key={otherEntity.value}>
                          {otherEntity.label}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {!isNew && (
                <ValidatedField id="ocorrencia-departamento" name="departamento" data-cy="departamento" label="Departamento" type="select">
                  <option value="" key="0" />
                  {departamentos
                    ? departamentos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
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
