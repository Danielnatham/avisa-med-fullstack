import dayjs from 'dayjs';
import { IGestorDepartamento } from 'app/shared/model/gestor-departamento.model';

export interface IEquipe {
  id?: number;
  idOcorrencia?: number;
  idG?: number;
  dataAtribuicao?: dayjs.Dayjs;
  dataFinalizacao?: dayjs.Dayjs;
  gestorDepartamento?: IGestorDepartamento | null;
}

export const defaultValue: Readonly<IEquipe> = {};
