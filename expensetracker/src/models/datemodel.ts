export namespace DateModel{
    
    export interface params{
        userId:number
        startDate:string
        endDate:string
    }

    export interface retrive  {
        id: number,
        userId: number,
        categoryId: number,
        amount: number,
        description: string,
        date: string
        data?:{
            message:null
        }
    }

    export const url=`bydaterange`
}
