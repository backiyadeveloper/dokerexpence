export namespace ViewCategory{

    export interface params{
        userId:number
    }

    export interface retrive{
        data:string[]
    }

    export const url=`getdistinctname`
}

export namespace Listofcategory{

    export interface params{
        userid:number,
        type:string
    }

    export interface retrive{
            date: string;       
            amount: number;     
            description: string; 
            id: number;         
            category: string;      
    }
    export const url=`getcategory`
}