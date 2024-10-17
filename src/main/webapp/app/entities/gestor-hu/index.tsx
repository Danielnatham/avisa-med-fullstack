import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GestorHu from './gestor-hu';
import GestorHuDetail from './gestor-hu-detail';
import GestorHuUpdate from './gestor-hu-update';
import GestorHuDeleteDialog from './gestor-hu-delete-dialog';

const GestorHuRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GestorHu />} />
    <Route path="new" element={<GestorHuUpdate />} />
    <Route path=":id">
      <Route index element={<GestorHuDetail />} />
      <Route path="edit" element={<GestorHuUpdate />} />
      <Route path="delete" element={<GestorHuDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GestorHuRoutes;
