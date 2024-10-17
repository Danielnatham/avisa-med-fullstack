import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipe.reducer';

export const EquipeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipeEntity = useAppSelector(state => state.equipe.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipeDetailsHeading">Equipe</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{equipeEntity.id}</dd>
          <dt>
            <span id="idOcorrencia">Id Ocorrencia</span>
          </dt>
          <dd>{equipeEntity.idOcorrencia}</dd>
          <dt>
            <span id="idG">Id G</span>
          </dt>
          <dd>{equipeEntity.idG}</dd>
          <dt>
            <span id="dataAtribuicao">Data Atribuicao</span>
          </dt>
          <dd>
            {equipeEntity.dataAtribuicao ? <TextFormat value={equipeEntity.dataAtribuicao} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dataFinalizacao">Data Finalizacao</span>
          </dt>
          <dd>
            {equipeEntity.dataFinalizacao ? <TextFormat value={equipeEntity.dataFinalizacao} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Gestor Departamento</dt>
          <dd>{equipeEntity.gestorDepartamento ? equipeEntity.gestorDepartamento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/equipe" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipe/${equipeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipeDetail;
