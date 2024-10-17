import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EquipeColaborador from './equipe-colaborador';
import EquipeColaboradorDetail from './equipe-colaborador-detail';
import EquipeColaboradorUpdate from './equipe-colaborador-update';
import EquipeColaboradorDeleteDialog from './equipe-colaborador-delete-dialog';

const EquipeColaboradorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EquipeColaborador />} />
    <Route path="new" element={<EquipeColaboradorUpdate />} />
    <Route path=":id">
      <Route index element={<EquipeColaboradorDetail />} />
      <Route path="edit" element={<EquipeColaboradorUpdate />} />
      <Route path="delete" element={<EquipeColaboradorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipeColaboradorRoutes;
