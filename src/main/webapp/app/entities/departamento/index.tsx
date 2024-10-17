import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Departamento from './departamento';
import DepartamentoDetail from './departamento-detail';
import DepartamentoUpdate from './departamento-update';
import DepartamentoDeleteDialog from './departamento-delete-dialog';

const DepartamentoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Departamento />} />
    <Route path="new" element={<DepartamentoUpdate />} />
    <Route path=":id">
      <Route index element={<DepartamentoDetail />} />
      <Route path="edit" element={<DepartamentoUpdate />} />
      <Route path="delete" element={<DepartamentoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DepartamentoRoutes;
