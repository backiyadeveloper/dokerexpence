import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ApiService } from "../apiservice/apiservice";
import { Login } from "../models/loginmodel";

const api = new ApiService();

export const loginservice = () => {
  const [loginPasswordVisible, setLoginPasswordVisible] = useState(false);
  const [signupPasswordVisible, setSignupPasswordVisible] = useState(false);
  const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);
  const [activeForm, setActiveForm] = useState<"login" | "signup">("login");
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [passwordErrorMessage, setpasswordErrorMessage] = useState<string>("");
  const [usernameErrorMessage, setusernameErrorMessage] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");
  const navigate = useNavigate(); 
  const showLogin = () => setActiveForm("login");
  const showSignUp = () => setActiveForm("signup");

  const toggleLoginPasswordVisibility = () => setLoginPasswordVisible(!loginPasswordVisible);
  const toggleSignupPasswordVisibility = () =>
    setSignupPasswordVisible(!signupPasswordVisible);
  const toggleConfirmPasswordVisibility = () =>
    setConfirmPasswordVisible(!confirmPasswordVisible);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSignup = async (e: React.FormEvent) => {
    e.preventDefault();
    const { username, password, confirmPassword } = formData;
    const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
    const usernamereg = /^[A-Za-z]{3,20}$/;

    if (!passwordPattern.test(password) && !usernamereg.test(username)) {
      setpasswordErrorMessage(
        "Password must include an uppercase letter, a digit, and a special character."
      );
      setusernameErrorMessage("Enter minimum three characters");
      return;
    }

    if (!passwordPattern.test(password)) {
      setpasswordErrorMessage(
        "Password must include an uppercase letter, a digit, and a special character."
      );
      return;
    }

    if (!usernamereg.test(username)) {
      setusernameErrorMessage("Enter minimum three characters");
      return;
    }

    if (password !== confirmPassword) {
      setErrorMessage("Passwords do not match.");
      return;
    }

    const encryptedPassword = btoa(password);
    const loginparams: Login.params = {
      username: username,
      password: encryptedPassword,
    };
    try{
      const response = await api.apisendrequest(Login.signupname,loginparams,null,navigate ) as Login.retrive;
      if (response.status) {
        setErrorMessage("");
        showLogin();
      } else {
        const message = await response.message;
        setErrorMessage(message);
      }}catch(error){
        console.error(error)
      }
  };

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    const { username, password } = formData;

    try {
      const encryptedPassword = btoa(password);
      const loginparams: Login.params = {
        username: username,
        password: encryptedPassword,
      };

      const response = await api.apisendrequest(Login.name,loginparams,null,navigate ) as Login.retrive;

      if (response.status) {
        const token: any = response.data?.token;
        localStorage.setItem("token", token);
        localStorage.setItem("username", response.data!.username);
        localStorage.setItem("userid", String(response.data?.userid));
        sessionStorage.setItem("isLoggedIn", "true");

        setTimeout(() => {
          navigate("/home");
        }, 100);
      } else if (!response.status) {
        setErrorMessage(response.message);
      } else {
        setErrorMessage("An error occurred during login.");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return {
    activeForm,
    formData,
    handleInputChange,
    errorMessage,
    showLogin,
    showSignUp,
    handleSignup,
    handleLogin,
    passwordErrorMessage,
    usernameErrorMessage,
    loginPasswordVisible,
    toggleLoginPasswordVisibility,
    signupPasswordVisible,
    toggleSignupPasswordVisibility,
    confirmPasswordVisible,
    toggleConfirmPasswordVisibility
  };
};
