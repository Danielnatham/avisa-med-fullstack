import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/usuario">
        Usuario
      </MenuItem>
      <MenuItem icon="asterisk" to="/colaborador">
        Colaborador
      </MenuItem>
      <MenuItem icon="asterisk" to="/cidadao-identificado">
        Cidadao Identificado
      </MenuItem>
      <MenuItem icon="asterisk" to="/gestor-hu">
        Gestor Hu
      </MenuItem>
      <MenuItem icon="asterisk" to="/gestor-departamento">
        Gestor Departamento
      </MenuItem>
      <MenuItem icon="asterisk" to="/departamento">
        Departamento
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipe">
        Equipe
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipe-colaborador">
        Equipe Colaborador
      </MenuItem>
      <MenuItem icon="asterisk" to="/ocorrencia">
        Ocorrencia
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
