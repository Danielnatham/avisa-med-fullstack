import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './colaborador.reducer';

export const ColaboradorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const colaboradorEntity = useAppSelector(state => state.colaborador.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="colaboradorDetailsHeading">Colaborador</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{colaboradorEntity.id}</dd>
          <dt>
            <span id="idDepartamento">Id Departamento</span>
          </dt>
          <dd>{colaboradorEntity.idDepartamento}</dd>
          <dt>
            <span id="idUsuario">Id Usuario</span>
          </dt>
          <dd>{colaboradorEntity.idUsuario}</dd>
          <dt>Usuario</dt>
          <dd>{colaboradorEntity.usuario ? colaboradorEntity.usuario.id : ''}</dd>
          <dt>Departamento</dt>
          <dd>{colaboradorEntity.departamento ? colaboradorEntity.departamento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/colaborador" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/colaborador/${colaboradorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ColaboradorDetail;
