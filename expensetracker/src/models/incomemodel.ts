export namespace IncomeParams{

    export interface params{
        userId:number;
        income:number;
    }

    export interface retrive{
        status:boolean | string
        message:string;
    }

    export const url=`adddata`
}