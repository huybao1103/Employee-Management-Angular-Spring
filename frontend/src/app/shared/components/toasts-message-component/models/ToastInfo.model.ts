export interface ToastInfo {
  type: ToastType;
  body: string;
  delay?: number;
}

export enum ToastType {
  Success = 'bg-success text-light',
  Error = 'bg-danger text-light',
  Info = 'bg-info text-light',
  Warning = 'bg-warning text-dark',
}