import { IUsuario } from 'app/shared/model/usuario.model';

export interface ICidadaoIdentificado {
  id?: number;
  idUsuario?: number;
  usuario?: IUsuario | null;
}

export const defaultValue: Readonly<ICidadaoIdentificado> = {};
