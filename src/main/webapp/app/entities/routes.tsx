import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Usuario from './usuario';
import Colaborador from './colaborador';
import CidadaoIdentificado from './cidadao-identificado';
import GestorHu from './gestor-hu';
import GestorDepartamento from './gestor-departamento';
import Departamento from './departamento';
import Equipe from './equipe';
import EquipeColaborador from './equipe-colaborador';
import Ocorrencia from './ocorrencia';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="usuario/*" element={<Usuario />} />
        <Route path="colaborador/*" element={<Colaborador />} />
        <Route path="cidadao-identificado/*" element={<CidadaoIdentificado />} />
        <Route path="gestor-hu/*" element={<GestorHu />} />
        <Route path="gestor-departamento/*" element={<GestorDepartamento />} />
        <Route path="departamento/*" element={<Departamento />} />
        <Route path="equipe/*" element={<Equipe />} />
        <Route path="equipe-colaborador/*" element={<EquipeColaborador />} />
        <Route path="ocorrencia/*" element={<Ocorrencia />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
