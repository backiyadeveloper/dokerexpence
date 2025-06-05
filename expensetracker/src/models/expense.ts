
export namespace Addexpense{

    export interface expenses{
        userId:number;
        categoryId:number;
        amount:number;
        description:string;
        date:string;
    }
    
    export interface categorydata{
        userid:number;
        categoryid:number;
        name:string;
        type:string;
    }

    export interface params{
        expense:expenses;
        category:categorydata;
    }

    export interface retrive{
        status:string;
        message:string
    }
    
    export const url=`addexpensecategory`
}