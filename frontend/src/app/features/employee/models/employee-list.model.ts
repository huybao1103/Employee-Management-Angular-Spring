import { IDepartmentModel } from "../../department/models/department.model";

export interface IEmployeeListModel {
    id?: string;
    name: string;
    email: string;
    salary: number;
    department: IDepartmentModel;
}