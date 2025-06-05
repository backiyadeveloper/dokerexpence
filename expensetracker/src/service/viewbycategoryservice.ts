import { useState, useEffect } from "react";
import { ApiService } from "../apiservice/apiservice";
import { ViewCategory, Listofcategory } from "../models/viewbycategorymodel";
import { useNavigate } from "react-router-dom";
const api = new ApiService();

export function UseViewCategory() {
  const [categories, setCategories] = useState<string[]>([]);
  
  const [type, setType] = useState<string>("");
  const [expense, setExpense] = useState<Listofcategory.retrive[]>([]);
  const navigate = useNavigate()
  const token = localStorage.getItem("token");

  const handleChange = (data: string) => {
    setType(data);
  };

  useEffect(() => {
    if (!token) {
      navigate("/")
    }
  },[])

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const userId = parseInt(localStorage.getItem("userid") || "0");
        if (!token || !userId) {
          throw new Error("Authentication token or user ID is missing");
        }

        const params: ViewCategory.params = { userId };
        const response = (await api.apisendrequest(ViewCategory.url,params,token, navigate)) as string[];
        
        if (response && response.length > 0) {
          setCategories(response);
        } else {
          throw new Error("No categories found");
        }
      } catch (err: any) {
        // setError(err.message || "An unknown error occurred");
      }
    
    };

    fetchCategories();
  }, []);

  const categorytable = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    try {

      const token = localStorage.getItem("token");
      const userId = parseInt(localStorage.getItem("userid") || "0");

      if (!token || !userId) {
        throw new Error("Authentication token or user ID is missing");
      }

      const params: Listofcategory.params = { userid: userId, type };
      const response = (await api.apisendrequest(
        Listofcategory.url,
        params,
        token, navigate
      )) as Listofcategory.retrive[];

      setExpense(response);
    } catch (err: any) {
      console.error("Failed to fetch category table data:", err.message);
    }
  }

  return { categories, handleChange, categorytable, expense };
}
