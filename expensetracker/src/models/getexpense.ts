export namespace Expense{

    export interface params{
        "userid":number
    }

    export interface retrive{
        "id": number,
        "userId": number,
        "categoryId": number,
        "amount": number,
        "categoryName": string,
        "description": string,
        "categoryType": string,
        "date": string
    }

    export const url=`getExpenses`;
}

export namespace UpdateExpense{

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
        status:boolean;
        message:string
    }
 
    export const update=`updatecategoryexpense`
}

export namespace Delete{

    export interface params{
        "userId":number,
        "categoryId":number,
    }

    export interface retrive{
        status:boolean
        message:string
    }
    
    export const url=`delete`;
}