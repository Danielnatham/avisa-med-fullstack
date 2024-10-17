import dayjs from 'dayjs';

export interface IUsuario {
  id?: number;
  nome?: string;
  telefone?: string;
  dataNascimento?: dayjs.Dayjs;
  email?: string;
  cpf?: string;
  senha?: string;
}

export const defaultValue: Readonly<IUsuario> = {};
