import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './usuario.reducer';

export const UsuarioDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const usuarioEntity = useAppSelector(state => state.usuario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="usuarioDetailsHeading">Usuario</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{usuarioEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{usuarioEntity.nome}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{usuarioEntity.telefone}</dd>
          <dt>
            <span id="dataNascimento">Data Nascimento</span>
          </dt>
          <dd>
            {usuarioEntity.dataNascimento ? (
              <TextFormat value={usuarioEntity.dataNascimento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{usuarioEntity.email}</dd>
          <dt>
            <span id="cpf">Cpf</span>
          </dt>
          <dd>{usuarioEntity.cpf}</dd>
          <dt>
            <span id="senha">Senha</span>
          </dt>
          <dd>{usuarioEntity.senha}</dd>
        </dl>
        <Button tag={Link} to="/usuario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/usuario/${usuarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UsuarioDetail;
