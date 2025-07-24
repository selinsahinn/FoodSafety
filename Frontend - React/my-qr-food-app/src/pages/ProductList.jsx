import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import "../ProductList.css";

export default function ProductList() {
  const [userRole, setUserRole] = useState("ANONIM");
  const [products, setProducts] = useState([]);
  const [formData, setFormData] = useState({
    product_name: "",
    origin: "",
    description: "",
  });
  const [editingId, setEditingId] = useState(null);
  const navigate = useNavigate();

  const isAdmin = userRole === "ADMIN";

  useEffect(() => {
    fetchUserRole();
    loadProducts();
  }, []);

  const fetchUserRole = () => {
    fetch("http://localhost:8080/api/auth/role", {
      credentials: "include",
    })
      .then((res) => {
        if (!res.ok) throw new Error("İstek başarısız");
        return res.text();
      })
      .then((role) => {
        console.log("Gelen rol:", role);
        setUserRole(role.toUpperCase());
      })
      .catch((err) => {
        console.error("Rol alınamadı:", err);
        setUserRole("ANONIM");
      });
  };

  const loadProducts = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/products", {
        withCredentials: true,
      });
      setProducts(res.data);
    } catch (err) {
      console.error("Ürünler yüklenemedi", err);
    }
  };

  const handleDelete = (id) => {
    Swal.fire({
      title: "Emin misiniz?",
      text: "Bu ürün silinecek!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Evet, sil",
      cancelButtonText: "Vazgeç",
    }).then(async (result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: "Siliniyor...",
          allowOutsideClick: false,
          didOpen: () => Swal.showLoading(),
        });

        try {
          await axios.delete(`http://localhost:8080/api/products/${id}`, {
            withCredentials: true,
          });

          Swal.fire({
            icon: "success",
            title: "Ürün silindi",
            timer: 1500,
            showConfirmButton: false,
          });

          loadProducts();
        } catch (err) {
          console.error("Silme hatası:", err);
          Swal.fire({
            icon: "error",
            title: "Hata",
            text: "Ürün silinemedi!",
          });
        }
      }
    });
  };

  const handleEdit = (product) => {
    setFormData({
      product_name: product.product_name,
      origin: product.origin,
      description: product.description || "",
    });
    setEditingId(product.product_id);
  };

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    Swal.fire({
      title: editingId ? "Güncelleniyor..." : "Kaydediliyor...",
      html: "Lütfen bekleyin...",
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading(),
    });

    const url = editingId
      ? `http://localhost:8080/api/products/${editingId}`
      : "http://localhost:8080/api/products";
    const method = editingId ? "put" : "post";

    try {
      await axios[method](url, formData, { withCredentials: true });

      Swal.fire({
        icon: "success",
        title: editingId ? "Ürün güncellendi" : "Ürün eklendi",
        timer: 1500,
        showConfirmButton: false,
      });

      setFormData({ product_name: "", origin: "", description: "" });
      setEditingId(null);
      loadProducts();
    } catch (err) {
      console.error("Kaydetme hatası:", err);
      Swal.fire({
        icon: "error",
        title: "Hata",
        text: "İşlem başarısız!",
      });
    }
  };

  return (
    <div className="product-container">
      <h1>Ürün Listesi</h1>

      <div className="table-wrapper">
        <table className="product-table">
          <thead>
            <tr>
              <th>İsim</th>
              <th>Menşei</th>
              <th>Açıklama</th>
              <th>QR</th>
              <th>QR Link</th>
              <th>İşlem</th>
            </tr>
          </thead>
          <tbody>
            {products.map((p) => (
              <tr key={p.product_id}>
                <td>{p.product_name}</td>
                <td>{p.origin}</td>
                <td>{p.description || ""}</td>
                <td>{p.qr_code || ""}</td>
                <td>
                  {(p.qrCodes || []).map((q) => (
                    <a
                      href={q.qrCodeValue}
                      target="_blank"
                      rel="noreferrer"
                      key={q.qrCodeValue}
                    >
                      {q.qrCodeValue}
                      <br />
                    </a>
                  ))}
                </td>
                <td>
                  {isAdmin && (
                    <>
                      <button onClick={() => handleEdit(p)}>Düzenle</button>
                      <button onClick={() => handleDelete(p.product_id)}>
                        Sil
                      </button>
                    </>
                  )}
                  <button
                    onClick={() =>
                      navigate(`/product-detail?id=${p.product_id}`)
                    }
                  >
                    Detay
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {isAdmin && (
        <>
          <h2>{editingId ? "Düzenle" : "Yeni Ürün"}</h2>
          <form className="product-form" onSubmit={handleSubmit}>
            <input
              type="text"
              name="product_name"
              placeholder="Ürün adı"
              value={formData.product_name}
              onChange={handleChange}
              required
            />
            <input
              type="text"
              name="origin"
              placeholder="Menşei"
              value={formData.origin}
              onChange={handleChange}
              required
            />
            <textarea
              name="description"
              placeholder="Açıklama"
              value={formData.description}
              onChange={handleChange}
            ></textarea>
            <button type="submit">Kaydet</button>
          </form>
        </>
      )}
    </div>
  );
}
