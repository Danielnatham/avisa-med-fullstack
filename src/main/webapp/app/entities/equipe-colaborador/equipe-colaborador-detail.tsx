import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipe-colaborador.reducer';

export const EquipeColaboradorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipeColaboradorEntity = useAppSelector(state => state.equipeColaborador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipeColaboradorDetailsHeading">Equipe Colaborador</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{equipeColaboradorEntity.id}</dd>
          <dt>
            <span id="idColaborador">Id Colaborador</span>
          </dt>
          <dd>{equipeColaboradorEntity.idColaborador}</dd>
          <dt>
            <span id="idEquipe">Id Equipe</span>
          </dt>
          <dd>{equipeColaboradorEntity.idEquipe}</dd>
          <dt>Equipe</dt>
          <dd>{equipeColaboradorEntity.equipe ? equipeColaboradorEntity.equipe.id : ''}</dd>
          <dt>Colaborador</dt>
          <dd>{equipeColaboradorEntity.colaborador ? equipeColaboradorEntity.colaborador.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/equipe-colaborador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipe-colaborador/${equipeColaboradorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipeColaboradorDetail;
