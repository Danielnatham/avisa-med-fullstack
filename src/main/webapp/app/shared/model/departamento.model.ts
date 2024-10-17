import { IGestorDepartamento } from 'app/shared/model/gestor-departamento.model';

export interface IDepartamento {
  id?: number;
  idGestorDepartamento?: number;
  nome?: string;
  descricao?: string;
  gestorDepartamento?: IGestorDepartamento | null;
}

export const defaultValue: Readonly<IDepartamento> = {};
