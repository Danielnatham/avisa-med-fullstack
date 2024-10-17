import { IColaborador } from 'app/shared/model/colaborador.model';

export interface IGestorDepartamento {
  id?: number;
  titulo?: string;
  descricao?: string;
  colaborador?: IColaborador | null;
}

export const defaultValue: Readonly<IGestorDepartamento> = {};
