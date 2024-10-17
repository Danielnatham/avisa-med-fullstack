import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gestor-departamento.reducer';

export const GestorDepartamentoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gestorDepartamentoEntity = useAppSelector(state => state.gestorDepartamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gestorDepartamentoDetailsHeading">Gestor Departamento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{gestorDepartamentoEntity.id}</dd>
          <dt>
            <span id="titulo">Titulo</span>
          </dt>
          <dd>{gestorDepartamentoEntity.titulo}</dd>
          <dt>
            <span id="descricao">Descricao</span>
          </dt>
          <dd>{gestorDepartamentoEntity.descricao}</dd>
          <dt>Colaborador</dt>
          <dd>{gestorDepartamentoEntity.colaborador ? gestorDepartamentoEntity.colaborador.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/gestor-departamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gestor-departamento/${gestorDepartamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GestorDepartamentoDetail;
