export namespace ReportModel {

    export interface retrive {
        [key: string]: string; 
    }

    export interface params {
        userId: number;   
    }

    export const url = "getcategoryreport"; 
}