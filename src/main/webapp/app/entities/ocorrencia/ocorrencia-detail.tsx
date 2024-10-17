import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ocorrencia.reducer';

export const OcorrenciaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ocorrenciaEntity = useAppSelector(state => state.ocorrencia.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ocorrenciaDetailsHeading">Ocorrencia</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{ocorrenciaEntity.id}</dd>
          <dt>
            <span id="idSolicitante">Id Solicitante</span>
          </dt>
          <dd>{ocorrenciaEntity.idSolicitante}</dd>
          <dt>
            <span id="idDepartamento">Id Departamento</span>
          </dt>
          <dd>{ocorrenciaEntity.idDepartamento}</dd>
          <dt>
            <span id="dataCriacao">Data Criacao</span>
          </dt>
          <dd>
            {ocorrenciaEntity.dataCriacao ? <TextFormat value={ocorrenciaEntity.dataCriacao} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dataResolucao">Data Resolucao</span>
          </dt>
          <dd>
            {ocorrenciaEntity.dataResolucao ? (
              <TextFormat value={ocorrenciaEntity.dataResolucao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="titulo">Titulo</span>
          </dt>
          <dd>{ocorrenciaEntity.titulo}</dd>
          <dt>
            <span id="descricao">Descricao</span>
          </dt>
          <dd>{ocorrenciaEntity.descricao}</dd>
          <dt>
            <span id="imagem">Imagem</span>
          </dt>
          <dd>{ocorrenciaEntity.imagem}</dd>
          <dt>
            <span id="situacao">Situacao</span>
          </dt>
          <dd>{ocorrenciaEntity.situacao}</dd>
          <dt>
            <span id="complexidade">Complexidade</span>
          </dt>
          <dd>{ocorrenciaEntity.complexidade}</dd>
          <dt>
            <span id="protocolo">Protocolo</span>
          </dt>
          <dd>{ocorrenciaEntity.protocolo}</dd>
          <dt>Cidadao Identificado</dt>
          <dd>{ocorrenciaEntity.cidadaoIdentificado ? ocorrenciaEntity.cidadaoIdentificado.id : ''}</dd>
          <dt>Departamento</dt>
          <dd>{ocorrenciaEntity.departamento ? ocorrenciaEntity.departamento.id : ''}</dd>
          <dt>Equipe</dt>
          <dd>{ocorrenciaEntity.equipe ? ocorrenciaEntity.equipe.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ocorrencia" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ocorrencia/${ocorrenciaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OcorrenciaDetail;
