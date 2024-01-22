import React from "react";
import "./Login.scss";
import InputField from "../components/InputFiled";

export default function Login() {
  return (
    <div className="loginContainer">
      <div className="loginMain">
      <div className="UserHeader">User Login/Sign Up</div>
        <div className="loginComponent">
          <div className="loginHeader">User Login</div>
          <div className="LoginBody">
            <label htmlFor="">UserName</label>
            <InputField />
          </div>
          <div className="loginFooter">
            <button>Login</button>
          </div>
        </div>
        <div className="signUpComponent">Sign Up </div>
      </div>
    </div>
  );
}
