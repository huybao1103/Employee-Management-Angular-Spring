import { IUserListModel } from "./user-list.model";

export interface IUpdateUserModel extends IUserListModel {
    password: string;
}