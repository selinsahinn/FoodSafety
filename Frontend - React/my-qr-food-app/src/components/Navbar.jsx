import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from '../food-safety-logo.png';
import './Navbar.css';

export default function Navbar() {
  const [role, setRole] = useState("ANONIM");
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/api/auth/role", {
      credentials: "include",
    })
      .then((res) => res.text())
      .then((data) => setRole(data.toUpperCase()))
      .catch(() => setRole("ANONIM"));
  }, []);

  const handleLogout = () => {
    fetch("http://localhost:8080/logout", {
      method: "POST",
      credentials: "include",
    }).then(() => {
      setRole("ANONIM");
      navigate("/login");
    });
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <img src={logo} alt="Logo" className="navbar-logo" />
        <Link to="/products" className="navbar-link">
          Ürünler
        </Link>
      </div>
      <div className="navbar-right">
        {role !== "ANONIM" ? (
          <>
            <span className="navbar-role" >Rol: {role}</span>
            <button className="navbar-button" onClick={handleLogout}>
              Çıkış
            </button>
          </>
        ) : (
          <Link to="/login">
            <button className="navbar-button">Giriş Yap</button>
          </Link>
        )}
      </div>
    </nav>
  );
}