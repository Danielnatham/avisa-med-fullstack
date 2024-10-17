import { IEquipe } from 'app/shared/model/equipe.model';
import { IColaborador } from 'app/shared/model/colaborador.model';

export interface IEquipeColaborador {
  id?: number;
  idColaborador?: number;
  idEquipe?: number;
  equipe?: IEquipe | null;
  colaborador?: IColaborador | null;
}

export const defaultValue: Readonly<IEquipeColaborador> = {};
