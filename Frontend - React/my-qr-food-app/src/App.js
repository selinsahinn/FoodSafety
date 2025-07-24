import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProductList from "./pages/ProductList";
import Login from "./pages/Login";
import ProductDetail from "./pages/ProductDetail";
import Navbar from "./components/Navbar";

function App() {
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/auth/role")
      .then((res) => res.text())
      .then((role) => setUserRole(role.toUpperCase()))
      .catch(() => setUserRole(null));
  }, []);

  // Rol bilgisi henüz gelmemişse basit bir yükleniyor gösterebilirsin:
  if (userRole === null) {
    return (
      <Router>
        <Navbar />
        <div>Yükleniyor...</div>
      </Router>
    );
  }

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<ProductList userRole={userRole} />} />
        <Route path="/product-detail" element={<ProductDetail />} />
        <Route path="/login" element={<Login />} />
        <Route path="/products" element={<ProductList userRole={userRole} />} />
      </Routes>
    </Router>
  );
}

export default App;
