import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntity, updateEntity, createEntity, reset } from './usuario.reducer';

export const UsuarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const usuarioEntity = useAppSelector(state => state.usuario.entity);
  const loading = useAppSelector(state => state.usuario.loading);
  const updating = useAppSelector(state => state.usuario.updating);
  const updateSuccess = useAppSelector(state => state.usuario.updateSuccess);

  const handleClose = () => {
    navigate('/usuario' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...usuarioEntity,
      ...values,
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
          ...usuarioEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="avisaMedFullstackApp.usuario.home.createOrEditLabel" data-cy="UsuarioCreateUpdateHeading">
            Criar ou editar Usuario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="usuario-id" label="Código" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nome"
                id="usuario-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 50, message: 'Este campo não pode ter mais de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Telefone"
                id="usuario-telefone"
                name="telefone"
                data-cy="telefone"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 50, message: 'Este campo não pode ter mais de 50 caracteres.' },
                }}
              />
              <ValidatedField
                label="Data Nascimento"
                id="usuario-dataNascimento"
                name="dataNascimento"
                data-cy="dataNascimento"
                type="date"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Email"
                id="usuario-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 320, message: 'Este campo não pode ter mais de 320 caracteres.' },
                }}
              />
              <ValidatedField
                label="Cpf"
                id="usuario-cpf"
                name="cpf"
                data-cy="cpf"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 14, message: 'Este campo não pode ter mais de 14 caracteres.' },
                }}
              />
              <ValidatedField
                label="Senha"
                id="usuario-senha"
                name="senha"
                data-cy="senha"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  maxLength: { value: 50, message: 'Este campo não pode ter mais de 50 caracteres.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/usuario" replace color="info">
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

export default UsuarioUpdate;
