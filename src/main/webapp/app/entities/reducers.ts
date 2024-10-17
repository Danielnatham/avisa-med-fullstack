import usuario from 'app/entities/usuario/usuario.reducer';
import colaborador from 'app/entities/colaborador/colaborador.reducer';
import cidadaoIdentificado from 'app/entities/cidadao-identificado/cidadao-identificado.reducer';
import gestorHu from 'app/entities/gestor-hu/gestor-hu.reducer';
import gestorDepartamento from 'app/entities/gestor-departamento/gestor-departamento.reducer';
import departamento from 'app/entities/departamento/departamento.reducer';
import equipe from 'app/entities/equipe/equipe.reducer';
import equipeColaborador from 'app/entities/equipe-colaborador/equipe-colaborador.reducer';
import ocorrencia from 'app/entities/ocorrencia/ocorrencia.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  usuario,
  colaborador,
  cidadaoIdentificado,
  gestorHu,
  gestorDepartamento,
  departamento,
  equipe,
  equipeColaborador,
  ocorrencia,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
