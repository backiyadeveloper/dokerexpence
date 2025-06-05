import { useEffect, useState } from "react"
import { DateModel } from "../models/datemodel"
import { ApiService } from "../apiservice/apiservice"
import { useNavigate } from "react-router-dom";
const api = new ApiService();
export function ViewDate() {
  const navigate = useNavigate()
  const [expensedate, setExpensedate] = useState({
    startDate: "",
    endDate: ""
  })
  const [message, setMessage] = useState<string>("");
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setExpensedate((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  useEffect(() => {
    if (!token) {
      navigate("/")
    }
  })
  const [data, setData] = useState<DateModel.retrive[]>([])
  const userId = parseInt(localStorage.getItem("userid") || ("0"))
  const token = localStorage.getItem("token")
  const viewbydate = async (e: React.MouseEvent<HTMLButtonElement>) => {
    const { startDate, endDate } = expensedate
    e.preventDefault();
    const params: DateModel.params = {
      userId,
      startDate,
      endDate
    }
    try {
      const response = await api.apisendrequest(DateModel.url, params, token, navigate) as DateModel.retrive[];
      if (!response) {
        setData(response);
        setMessage(""); 
      } else {
        setData([]); 
        console.log("dkjfhsjk")
        setMessage("No data available for the selected date range.");
      }
    } catch (error) {
      console.log(error);
    }
  }

  return {
    handleInputChange, viewbydate, data,message
  }
  
}