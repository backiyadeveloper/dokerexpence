import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Addexpense } from "../models/expense";
import { ApiService } from "../apiservice/apiservice";

const api = new ApiService();

const useAddExpense = () => {
  
  const [expensedata, setExpensedata] = useState<Partial<{
    categoryid: number;
    amount: number;
    description: string;
    date: string;
    Expensename: string;
    }>>({});
  const [message, setMessage] = useState<string | null>(null);
  const [expensemessage,setExpensemesage]=useState<string | null>()
  const [descriptionmessage,setDescriptionmessage]=useState<string | null>()
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  useEffect(() => {
    if (!token) {
      navigate("/");
    }
  }, [token, navigate]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setExpensedata((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const addexpenses = async (e: React.FormEvent) => {
    e.preventDefault();
    const { categoryid, amount, description, date, Expensename } = expensedata;
    const testreg=/^[a-zA-Z]{3,}$/
    if (Expensename !== undefined) {
      if (!testreg.test(Expensename)) {
        setExpensemesage("give a valid expense with minimum 3 charector and without numbers");
        return
      }

    } 
    if (description !== undefined) {
      if (!testreg.test(description)) {
        setDescriptionmessage("give a valid expense with minimum 3 charector");
        return
      }
      
    } 
    if (!categoryid || !amount || !description || !date || !Expensename) {
      setMessage("Please fill all fields!");
      return;
    }
    const userId = parseInt(localStorage.getItem("userid") ?? "0");

    const expense: Addexpense.expenses = {
      userId,
      categoryId: categoryid,
      amount: amount,
      description: description,
      date: date,
    };

    const category: Addexpense.categorydata = {
      userid: userId,
      categoryid: categoryid,
      name: Expensename,
      type: description,
    };

    const addexpensecategory: Addexpense.params = { expense, category };

    try {
      const response = await api.apisendrequest(
        Addexpense.url,
        addexpensecategory,
        token,
        navigate
      ) as Addexpense.retrive;

      if (response.status === "success") {
        setMessage("Expense added successfully!");
        setExpensedata({});
        navigate("/home");
      } else if (response.status === "faild") {
        setMessage(response.message);
      } else {
        navigate("/");
      }
    } catch (error: unknown) {
      setMessage(
        error instanceof Error
          ? `Failed to add expense: ${error.message}`
          : "An unknown error occurred. Please try again."
      );
    }
  };

  return {
    addexpenses,
    handleInputChange,
    expensedata,
    message,
    expensemessage,
    descriptionmessage 
  };
};

export default useAddExpense;
