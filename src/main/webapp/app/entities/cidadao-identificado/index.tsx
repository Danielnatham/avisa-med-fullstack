import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CidadaoIdentificado from './cidadao-identificado';
import CidadaoIdentificadoDetail from './cidadao-identificado-detail';
import CidadaoIdentificadoUpdate from './cidadao-identificado-update';
import CidadaoIdentificadoDeleteDialog from './cidadao-identificado-delete-dialog';

const CidadaoIdentificadoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CidadaoIdentificado />} />
    <Route path="new" element={<CidadaoIdentificadoUpdate />} />
    <Route path=":id">
      <Route index element={<CidadaoIdentificadoDetail />} />
      <Route path="edit" element={<CidadaoIdentificadoUpdate />} />
      <Route path="delete" element={<CidadaoIdentificadoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CidadaoIdentificadoRoutes;
