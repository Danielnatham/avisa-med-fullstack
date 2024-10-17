import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ocorrencia from './ocorrencia';
import OcorrenciaDetail from './ocorrencia-detail';
import OcorrenciaUpdate from './ocorrencia-update';
import OcorrenciaDeleteDialog from './ocorrencia-delete-dialog';

const OcorrenciaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ocorrencia />} />
    <Route path="new" element={<OcorrenciaUpdate />} />
    <Route path=":id">
      <Route index element={<OcorrenciaDetail />} />
      <Route path="edit" element={<OcorrenciaUpdate />} />
      <Route path="delete" element={<OcorrenciaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OcorrenciaRoutes;
