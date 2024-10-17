import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cidadao-identificado.reducer';

export const CidadaoIdentificadoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cidadaoIdentificadoEntity = useAppSelector(state => state.cidadaoIdentificado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cidadaoIdentificadoDetailsHeading">Cidadao Identificado</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{cidadaoIdentificadoEntity.id}</dd>
          <dt>
            <span id="idUsuario">Id Usuario</span>
          </dt>
          <dd>{cidadaoIdentificadoEntity.idUsuario}</dd>
          <dt>Usuario</dt>
          <dd>{cidadaoIdentificadoEntity.usuario ? cidadaoIdentificadoEntity.usuario.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cidadao-identificado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cidadao-identificado/${cidadaoIdentificadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CidadaoIdentificadoDetail;
