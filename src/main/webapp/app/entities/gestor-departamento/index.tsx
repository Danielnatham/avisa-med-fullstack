import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GestorDepartamento from './gestor-departamento';
import GestorDepartamentoDetail from './gestor-departamento-detail';
import GestorDepartamentoUpdate from './gestor-departamento-update';
import GestorDepartamentoDeleteDialog from './gestor-departamento-delete-dialog';

const GestorDepartamentoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GestorDepartamento />} />
    <Route path="new" element={<GestorDepartamentoUpdate />} />
    <Route path=":id">
      <Route index element={<GestorDepartamentoDetail />} />
      <Route path="edit" element={<GestorDepartamentoUpdate />} />
      <Route path="delete" element={<GestorDepartamentoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GestorDepartamentoRoutes;
