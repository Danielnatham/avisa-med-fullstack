import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      <MenuItem icon="asterisk" to="/departamento">
        Departamento
      </MenuItem>
      <MenuItem icon="asterisk" to="/ocorrencia">
        Ocorrência
      </MenuItem>
    </>
  );
};

export default EntitiesMenu;
