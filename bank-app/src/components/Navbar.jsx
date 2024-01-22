import React from "react";
import "./Navbar.scss";
import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <div className="navbar">
      <div className="logo">
        <code>Apna🏦Bank</code>
      </div>
      <div className="link">
        <div>
          <Link to="/">HOME</Link>
        </div>
        <div>
          <Link to="/login">LOGIN</Link>
        </div>
        <div>
          <Link to="/contact">CONTACT US</Link>
        </div>
        <div>
          <Link to="/notices">NOTICES</Link>
        </div>
      </div>
    </div>
  );
}
