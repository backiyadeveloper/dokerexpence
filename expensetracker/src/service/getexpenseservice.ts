import { Expense, Delete, UpdateExpense } from "../models/getexpense";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ApiService } from "../apiservice/apiservice";

const api = new ApiService();

export function Getexpense() {

  const [editableExpense, setEditableExpense] = useState<Expense.retrive | null>(null);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const userid = localStorage.getItem("userid");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [expense, setExpense] = useState<Expense.retrive[]>([]);
  const [trigger, setTrigger] = useState(false);
  const [message,setMessage]=useState<string>()
  const handleEditClick = (expense: Expense.retrive) => {
    setEditableExpense(expense);
    setIsModalOpen(true);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    if (editableExpense) {
      setEditableExpense((prevExpense) => ({
        ...prevExpense!,
        [id]: id === "amount" ? parseFloat(value) : value,
      }));
    }
  };

  const handleSaveChanges = (e: React.FormEvent) => {
    e.preventDefault();
    if (editableExpense) saveChanges(editableExpense);
  };

  const saveChanges = async (updatedExpense: Expense.retrive) => {
    if (!updatedExpense.categoryId || !updatedExpense.amount || !updatedExpense.description || !updatedExpense.date || !updatedExpense.categoryName) {
      setMessage("Please fill all fields!");
      return;
    }
    const expense: UpdateExpense.expenses = {
      userId: updatedExpense.userId,
      categoryId: updatedExpense.categoryId,
      amount: updatedExpense.amount,
      description: updatedExpense.description,
      date: updatedExpense.date,
    };

    const category: UpdateExpense.categorydata = {
      userid: updatedExpense.userId,
      categoryid: updatedExpense.categoryId,
      name: updatedExpense.categoryName,
      type: updatedExpense.description,
    };

    const addexpensecategory: UpdateExpense.params = {
      expense,
      category,
    };

    try {
      const response = await api.apisendrequest(UpdateExpense.update, addexpensecategory, token, navigate) as UpdateExpense.retrive;

      if (response.status) {
        setExpense((prevExpenses) =>
          prevExpenses.map((expense) =>
            expense.categoryId === updatedExpense.categoryId &&
              expense.userId === updatedExpense.userId
              ? updatedExpense
              : expense
          )
        );
        closeModal();
      }
    } catch (error) {
      console.error("Error updating expense:", error);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    if (!token) {
      console.error("Token is missing. Redirecting to login...");
      navigate("/");
      return;
    }

    const fetchExpenses = async () => {
      if (!userid) {
        console.error("Missing user ID. Redirecting to login...");
        navigate("/");
        return;
      }

      const userId: Expense.params = {
        userid: parseInt(userid, 10),
      };

      try {
        const data = await api.apisendrequest(Expense.url, userId, token, navigate) as Expense.retrive[];
        setExpense(data);
      } catch (error) {
        console.error("Error fetching expenses:", error);
      }
    };

    fetchExpenses();
  }, [userid, token, trigger]);

  const deletecategory = async (userid: number,categoryid: number) => {
    const isConfirmed = confirm("Are you sure you want to delete this data?");
    if (!isConfirmed) 
      return;

    if (!token) {
      console.error("Token is missing. Redirecting to login...");
      navigate("/");
      return;
    }

    const deleteparams: Delete.params = {
      userId: userid,
      categoryId: categoryid,
    };

    try {
      const response = await api.apisendrequest(Delete.url, deleteparams, token, navigate) as Delete.retrive;
      if (response.status) {
        setTrigger(true);
      }
    } catch (error) {
      console.error("Error deleting category:", error);
    }
  };

  return {
    expense,
    deletecategory,
    handleEditClick,
    isModalOpen,
    closeModal,
    editableExpense,
    handleSaveChanges,
    handleInputChange,
    message
  };
}
