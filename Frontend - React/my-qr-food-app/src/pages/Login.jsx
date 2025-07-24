import React, { useState } from "react";
import axios from "axios";
import qs from "qs";
import Swal from "sweetalert2";
import "../Login.css";
import logo from "../food-safety-logo.png"; // Logo dosyanı buraya ekle

export default function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({ username: "", password: "" });

  const handleSubmit = async (e) => {
    e.preventDefault();

    Swal.fire({
      title: "Giriş yapılıyor...",
      html: "Lütfen bekleyin...",
      timer: 1500,
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      },
    });

    setTimeout(async () => {
      try {
        const response = await axios.post(
          "http://localhost:8080/login",
          qs.stringify(formData),
          {
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            withCredentials: true,
          }
        );

        Swal.fire({
          icon: "success",
          title: response.data.message || "Giriş başarılı",
          showConfirmButton: false,
          timer: 1500,
        });

        setTimeout(() => {
          window.location.href = "/products";
        }, 1500);
      } catch (err) {
        Swal.fire({
          icon: "error",
          title: "Hata",
          text: err.response?.data?.message || "Giriş başarısız!",
        });
      }
    }, 1500);
  };

  const togglePassword = () => setShowPassword((prev) => !prev);
  const handleChange = (e) =>
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));

  return (
    <div className="login-wrapper">
      <form className="login-form" onSubmit={handleSubmit}>
        <img src={logo} alt="Gıda Güvenliği Logo" className="login-logo" />
        <h1 className="login-title">Giriş Yap</h1>

        <label htmlFor="username" className="login-label">
          Kullanıcı Adı
        </label>
        <input
          id="username"
          name="username"
          value={formData.username}
          onChange={handleChange}
          className="login-input"
          autoComplete="username"
          required
        />

        <label htmlFor="password" className="login-label">
          Şifre
        </label>
        <input
          id="password"
          name="password"
          type={showPassword ? "text" : "password"}
          value={formData.password}
          onChange={handleChange}
          className="login-input"
          autoComplete="current-password"
          required
        />

        <span className="toggle-password" onClick={togglePassword}>
          {showPassword ? "Şifreyi gizle" : "Şifreyi göster"}
        </span>

        <button type="submit" className="login-button">
          Giriş
        </button>
      </form>
    </div>
  );
}
