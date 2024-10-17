import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './ocorrencia.reducer';

export const Ocorrencia = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const ocorrenciaList = useAppSelector(state => state.ocorrencia.entities);
  const loading = useAppSelector(state => state.ocorrencia.loading);
  const totalItems = useAppSelector(state => state.ocorrencia.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="ocorrencia-heading" data-cy="OcorrenciaHeading">
        Ocorrencias
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Atualizar lista
          </Button>
          <Link to="/ocorrencia/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Criar novo Ocorrencia
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ocorrenciaList && ocorrenciaList.length > 0 ? (
          <Table responsive striped hover bordered className="text-center">
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('titulo')}>
                  Titulo <FontAwesomeIcon icon={getSortIconByFieldName('titulo')} />
                </th>
                <th className="hand" onClick={sort('descricao')}>
                  Descricao <FontAwesomeIcon icon={getSortIconByFieldName('descricao')} />
                </th>
                <th className="hand" onClick={sort('dataCriacao')}>
                  Data Criacao <FontAwesomeIcon icon={getSortIconByFieldName('dataCriacao')} />
                </th>
                <th className="hand" onClick={sort('dataResolucao')}>
                  Data Resolucao <FontAwesomeIcon icon={getSortIconByFieldName('dataResolucao')} />
                </th>
                <th className="hand" onClick={sort('situacao')}>
                  Situacao <FontAwesomeIcon icon={getSortIconByFieldName('situacao')} />
                </th>
                <th className="hand" onClick={sort('complexidade')}>
                  Complexidade <FontAwesomeIcon icon={getSortIconByFieldName('complexidade')} />
                </th>
                <th className="hand" onClick={sort('protocolo')}>
                  Protocolo <FontAwesomeIcon icon={getSortIconByFieldName('protocolo')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ocorrenciaList.map((ocorrencia, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ocorrencia/${ocorrencia.id}`} color="link" size="sm">
                      {ocorrencia.id}
                    </Button>
                  </td>
                  <td>{ocorrencia.titulo}</td>
                  <td>{ocorrencia.descricao}</td>
                  <td>
                    {ocorrencia.dataCriacao ? <TextFormat type="date" value={ocorrencia.dataCriacao} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {ocorrencia.dataResolucao ? <TextFormat type="date" value={ocorrencia.dataResolucao} format={APP_DATE_FORMAT} /> : '-'}
                  </td>
                  <td>{ocorrencia.situacao}</td>
                  <td>{ocorrencia.complexidade}</td>
                  <td>{ocorrencia.protocolo}</td>
                  <td className="d-flex text-end justify-content-center align-content-center">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ocorrencia/${ocorrencia.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Visualizar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/ocorrencia/${ocorrencia.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Atribuir</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/ocorrencia/${ocorrencia.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Excluir</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Nenhum Ocorrencia encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={ocorrenciaList && ocorrenciaList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Ocorrencia;
