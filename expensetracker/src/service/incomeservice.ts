import React, { useState ,useEffect} from "react";
import {IncomeParams} from "../models/incomemodel"
import {ApiService} from "../apiservice/apiservice"
import { useNavigate } from "react-router-dom";
const api=new ApiService()

export  function  Income(){
    const navigate=useNavigate()
    const [amount,setAmount]=useState<number>();
    const [errormessage,setErrorMessage]=useState<String>()
    const token=localStorage.getItem("token")

    const handleIncomeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = Number(e.target.value);
      setAmount(value); 
    };

    const  addincomedtata=async ()=>{
      if (amount === undefined) {
        throw new Error("Income is required and cannot be undefined");
      }

      const params: IncomeParams.params={
        userId:parseInt(localStorage.getItem("userid") || ("0")),
        income:amount
      }
      try{
        const response=await api.apisendrequest(IncomeParams.url,params,token,navigate) as IncomeParams.retrive;
        console.log(response.status)
        if(response.status===true){
          navigate("/user")
        }
        else if(!response.status){
          setErrorMessage(response.message);
        }
        else{
          navigate("/")
        }
      }catch(error){
        console.error(error)
      }
    }

    useEffect(()=>{
      if(!token){
        navigate("/")
      }
    })
    
    return {
      handleIncomeChange,
        addincomedtata,
        errormessage
    }
}