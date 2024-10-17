import dayjs from 'dayjs';
import { ICidadaoIdentificado } from 'app/shared/model/cidadao-identificado.model';
import { IDepartamento } from 'app/shared/model/departamento.model';
import { IEquipe } from 'app/shared/model/equipe.model';

export interface IOcorrencia {
  id?: number;
  idSolicitante?: number | null;
  idDepartamento?: number;
  dataCriacao?: dayjs.Dayjs;
  dataResolucao?: dayjs.Dayjs;
  titulo?: string;
  descricao?: string;
  imagem?: string;
  situacao?: string;
  complexidade?: number;
  protocolo?: string;
  cidadaoIdentificado?: ICidadaoIdentificado | null;
  departamento?: IDepartamento | null;
  equipe?: IEquipe | null;
}

export const defaultValue: Readonly<IOcorrencia> = {};
