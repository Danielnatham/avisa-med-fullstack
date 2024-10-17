import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gestor-hu.reducer';

export const GestorHuDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gestorHuEntity = useAppSelector(state => state.gestorHu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gestorHuDetailsHeading">Gestor Hu</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{gestorHuEntity.id}</dd>
          <dt>
            <span id="idColaborador">Id Colaborador</span>
          </dt>
          <dd>{gestorHuEntity.idColaborador}</dd>
          <dt>Colaborador</dt>
          <dd>{gestorHuEntity.colaborador ? gestorHuEntity.colaborador.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/gestor-hu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gestor-hu/${gestorHuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GestorHuDetail;
