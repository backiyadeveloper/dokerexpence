import { useEffect, useState } from "react";
import { ReportModel } from "../models/reportmodel";
import { ApiService } from "../apiservice/apiservice";
import { useNavigate } from "react-router-dom";
const api = new ApiService();

export function FetchReport() {
    const [data, setData] = useState<ReportModel.retrive >();
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const navigate=useNavigate()

    useEffect(() => {
        async function fetchData() {
            try {
                const token = localStorage.getItem("token");
                if(!token){
                    navigate("/")
                  }
                const userId = parseInt(localStorage.getItem("userid") || "0");
                if (!token || !userId) {
                    throw new Error("Authentication token or user ID is missing.");
                }

                const params: ReportModel.params = { userId };
                const response = await api.apisendrequest(ReportModel.url,params,token,navigate) as ReportModel.retrive;     
                setData(response);
                setLoading(false);
            } catch (err: any) {
                setError(err.message || "An unknown error occurred");
                setLoading(false);
            }
        }
        fetchData();
    }, []); 
    
    return { data, loading, error };
}
