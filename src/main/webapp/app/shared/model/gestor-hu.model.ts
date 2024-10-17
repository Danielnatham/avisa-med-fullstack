import { IColaborador } from 'app/shared/model/colaborador.model';

export interface IGestorHu {
  id?: number;
  idColaborador?: number;
  colaborador?: IColaborador | null;
}

export const defaultValue: Readonly<IGestorHu> = {};
