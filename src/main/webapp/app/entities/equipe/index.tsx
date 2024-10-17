import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Equipe from './equipe';
import EquipeDetail from './equipe-detail';
import EquipeUpdate from './equipe-update';
import EquipeDeleteDialog from './equipe-delete-dialog';

const EquipeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Equipe />} />
    <Route path="new" element={<EquipeUpdate />} />
    <Route path=":id">
      <Route index element={<EquipeDetail />} />
      <Route path="edit" element={<EquipeUpdate />} />
      <Route path="delete" element={<EquipeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipeRoutes;
