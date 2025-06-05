export namespace UserData{

    export interface params{
        userid:number
    }

    export interface retrive{
        userId:number
        income:number
    }

    export const api=`getincomebyid`
    export const updateincome=`updateincome`
}

export namespace UpdateUserData {
    
    export interface params {
        userId: number; 
        income: number; 
    }

    export interface retrive {
        status: boolean; 
        message:string
    }

    export const updateincome = 'updateincome';
}
