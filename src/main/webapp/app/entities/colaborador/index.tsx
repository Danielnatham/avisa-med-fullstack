import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Colaborador from './colaborador';
import ColaboradorDetail from './colaborador-detail';
import ColaboradorUpdate from './colaborador-update';
import ColaboradorDeleteDialog from './colaborador-delete-dialog';

const ColaboradorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Colaborador />} />
    <Route path="new" element={<ColaboradorUpdate />} />
    <Route path=":id">
      <Route index element={<ColaboradorDetail />} />
      <Route path="edit" element={<ColaboradorUpdate />} />
      <Route path="delete" element={<ColaboradorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ColaboradorRoutes;
