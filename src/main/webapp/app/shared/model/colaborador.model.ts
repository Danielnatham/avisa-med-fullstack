import { IUsuario } from 'app/shared/model/usuario.model';
import { IDepartamento } from 'app/shared/model/departamento.model';

export interface IColaborador {
  id?: number;
  idDepartamento?: number;
  idUsuario?: number;
  usuario?: IUsuario | null;
  departamento?: IDepartamento | null;
}

export const defaultValue: Readonly<IColaborador> = {};
