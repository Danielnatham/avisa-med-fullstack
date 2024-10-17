import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      <MenuItem icon="asterisk" to="/departamento">
        Departamento
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipe">
        Equipe
      </MenuItem>
      <MenuItem icon="asterisk" to="/ocorrencia">
        Ocorrência
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
