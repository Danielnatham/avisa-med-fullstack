import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './departamento.reducer';

export const DepartamentoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const departamentoEntity = useAppSelector(state => state.departamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="departamentoDetailsHeading">Departamento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{departamentoEntity.id}</dd>
          <dt>
            <span id="idGestorDepartamento">Id Gestor Departamento</span>
          </dt>
          <dd>{departamentoEntity.idGestorDepartamento}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{departamentoEntity.nome}</dd>
          <dt>
            <span id="descricao">Descricao</span>
          </dt>
          <dd>{departamentoEntity.descricao}</dd>
          <dt>Gestor Departamento</dt>
          <dd>{departamentoEntity.gestorDepartamento ? departamentoEntity.gestorDepartamento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/departamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/departamento/${departamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepartamentoDetail;
