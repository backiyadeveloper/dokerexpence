import { loginservice } from "../../service/loginservice";
import "./login.css";

const Login: React.FC = () => {
  const {
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
    toggleConfirmPasswordVisibility} = loginservice();

  return (
    <>
     <div className="parentdiv">
        <div className="parentdiv1">
          <div className="login">
            <div>
              <h1>LOGIN</h1>
              <h1>Your Account</h1>
              <button
                onClick={showLogin}
                className={activeForm === "login" ? "active-btn" : ""}>
                Login
              </button>
              <button
                onClick={showSignUp}
                className={activeForm === "signup" ? "active-btn" : ""}>
                Sign Up
              </button>
            </div>
          </div>
          <div className="formdiv">
            {activeForm === "login" && (
              <form
                id="loginForm"
                onSubmit={handleLogin}
                className="loginform active-form">
                <h1>Login</h1>
                <label htmlFor="username">Username</label>
                <div className="password-container">
                  <input type="text" name="username"  value={formData.username} onChange={handleInputChange} className="signinput" required/>
                </div>
                <label htmlFor="password">Password</label>
                <div className="password-container">
                  <input type={loginPasswordVisible ? "text" : "password"} name="password" value={formData.password} onChange={handleInputChange} className="signinput" required/>
                  <span
                    onClick={toggleLoginPasswordVisibility}
                    className="password-toggle-icon">
                    {loginPasswordVisible ? "🙈" : "👁️"}
                  </span>
                </div>
                <button type="submit" className="incomexpens">
                  LOGIN
                </button>
                {errorMessage && <span className="error">{errorMessage}</span>}
                <p>
                  Don't have an account?{" "}
                  <span onClick={showSignUp} className="toggle-link">
                    Sign up
                  </span>
                </p>
              </form>
            )}

            {activeForm === "signup" && (
              <form
                id="signUpForm"
                onSubmit={handleSignup}
                className="loginform active-form">
                  <h1>Sign Up</h1>
                  <label htmlFor="newUsername">Username</label>
                  <div className="password-container">
                    <input  type="text"  name="username" value={formData.username} onChange={handleInputChange}  className="signinput"  required/>
                  </div>
                  {usernameErrorMessage && (
                    <span className="error">{usernameErrorMessage}</span>
                  )}
                  <label htmlFor="newPassword">Password</label>
                  <div className="password-container">
                    <input  type={signupPasswordVisible ? "text" : "password"}  name="password"  value={formData.password}  onChange={handleInputChange}  className="signinput"  required
                    />
                    <span onClick={toggleSignupPasswordVisibility} className="password-toggle-icon">
                      {signupPasswordVisible ? "🔓" : "🔒"}
                    </span>
                  </div>
                  {passwordErrorMessage && (<span className="error">{passwordErrorMessage}</span>)}
                  <label htmlFor="confirmPassword">Confirm Password</label>
                  <div className="password-container">
                    <input type={confirmPasswordVisible ? "text" : "password"} name="confirmPassword" value={formData.confirmPassword}  onChange={handleInputChange}  className="signinput"  required
                    />
                    <span
                      onClick={toggleConfirmPasswordVisibility}
                      className="password-toggle-icon">
                      {confirmPasswordVisible ? "🔓" : "🔒"}
                    </span>
                  </div>
                  <button type="submit" className="incomexpens">SIGN UP</button>
                  {errorMessage && <span className="error">{errorMessage}</span>}
              </form>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default Login;
