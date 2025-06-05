import { useEffect, useState } from "react";
import { ApiService } from "../apiservice/apiservice";
import { UserData, UpdateUserData } from "../models/usermodel";
import { useNavigate } from "react-router-dom";

export function Displayuser() {
  const api = new ApiService();
  const userId = localStorage.getItem("userid");
  const token = localStorage.getItem("token");
  const [userdetails, setUserdetails] = useState<UserData.retrive[]>([]);
  const [edituser, setEdituser] = useState<UserData.retrive | null>(null);
  const [isopen, setOpen] = useState(false);
  const navigate = useNavigate()

  useEffect(() => {
    if (!token) {
      navigate("/")
    }
  })

  useEffect(() => {
    const params: UserData.params = {
      userid: parseInt(userId || "0"),
    };

    async function userapi() {
      try {
        const response = await api.apisendrequest(UserData.api, params, token, navigate) as UserData.retrive[];
        setUserdetails(response);
      } catch (err) {
        console.log(err);
      }
    }
    userapi();
  }, []);

  function isModelOpen(user: UserData.retrive) {
    setEdituser(user);
    setOpen(true);
  }

  function isclosed() {
    setOpen(false);
  }
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;

    if (edituser) {
      setEdituser((prevUser) => ({
        ...prevUser!,
        income: id === "editIncome" ? parseFloat(value) : prevUser!.income,
      }));
    }
  };

  const handlesave = (e: React.FormEvent) => {
    e.preventDefault();
    if (edituser) saveChanges(edituser);
  };

  const saveChanges = async (edituser: UserData.retrive) => {
    try {
      const params: UpdateUserData.params = {
        userId: edituser.userId,
        income: edituser.income,
      };
      const response = await api.apisendrequest(UpdateUserData.updateincome, params, token, navigate) as UpdateUserData.retrive;
      if (response.status) {
        setUserdetails((prevDetails) =>
          prevDetails.map((user) =>
            user.userId === edituser.userId ? edituser : user
          )
        );
        isclosed();
      } else {
        console.error("Error updating income:", response);
      }
    } catch (error) {
      console.error("Error in saveChanges:", error);
    }
  };
  return { userdetails, isopen, isModelOpen, isclosed, edituser, handleInputChange, handlesave };
}
