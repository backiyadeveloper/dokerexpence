export namespace Login{

  export interface params{
      username: string;
      password: string;
  };
  
  export interface retrive {
      status: boolean;  
      message: string;
      data?: {
        token: string;  
        userid:number;
        username:string;
      };
  }

  export const name=`login`;
  export const signupname=`user`;

}