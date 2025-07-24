import React, { useEffect, useState } from "react";
import { FaCheckCircle, FaTimesCircle, FaPlus } from "react-icons/fa";
import axios from "axios";
import Swal from "sweetalert2";

const allowedRoles = ["PRODUCER", "SELLER", "ADMIN"];

export default function ProductDetail() {
  const [userRole, setUserRole] = useState("ANONIM");
  const [loading, setLoading] = useState(true);
  const [verifyResult, setVerifyResult] = useState(null);
  const [product, setProduct] = useState(null);
  const [error, setError] = useState(null);
  const [blockData, setBlockData] = useState("");
  const [addingBlock, setAddingBlock] = useState(false);

  const params = new URLSearchParams(window.location.search);
  const product_id = params.get("id");

  useEffect(() => {
    fetch("http://localhost:8080/api/auth/role", {
      credentials: "include",
    })
      .then((res) => {
        if (!res.ok) throw new Error("İstek başarısız");
        return res.text();
      })
      .then((role) => setUserRole(role.toUpperCase()))
      .catch(() => setUserRole("ANONIM"));
  }, []);

  useEffect(() => {
    if (!product_id) {
      setError("Geçersiz ürün ID.");
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const verifyRes = await axios.get(
          `http://localhost:8080/api/blockchain/verify/${product_id}`,
          { withCredentials: true }
        );
        setVerifyResult(verifyRes.data);

        const productRes = await axios.get(
          `http://localhost:8080/api/products/${product_id}`,
          { withCredentials: true }
        );
        setProduct(productRes.data);
      } catch (err) {
        setError("Veri alınırken hata oluştu: " + err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [product_id]);

  const handleAddBlock = (e) => {
    e.preventDefault();

    if (!blockData.trim()) {
      Swal.fire({
        icon: "warning",
        title: "Eksik veri",
        text: "Lütfen blok verisi girin.",
      });
      return;
    }

    Swal.fire({
      title: "Blok ekleniyor...",
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading(),
    });

    setAddingBlock(true);

    axios
      .post(
        "http://localhost:8080/api/blockchain/add",
        {
          productId: parseInt(product_id),
          data: blockData,
        },
        { withCredentials: true }
      )
      .then(() => {
        Swal.fire({
          icon: "success",
          title: "Blok başarıyla eklendi!",
          timer: 1500,
          showConfirmButton: false,
        }).then(() => {
          window.location.reload();
        });
      })
      .catch((err) => {
        Swal.fire({
          icon: "error",
          title: "Hata oluştu",
          text: err.response?.data || err.message,
        });
      })
      .finally(() => {
        setAddingBlock(false);
      });
  };

  if (error) return <p style={{ padding: 20, color: "red" }}>{error}</p>;
  if (loading) return <p style={{ padding: 20 }}>⏳ Yükleniyor...</p>;

  const isValid =
    verifyResult?.isValid === true || verifyResult?.isValid === "true";

  return (
    <div style={styles.pageWrapper}>
      <div style={styles.container}>
        <h1 style={styles.title}>Ürün Detayı</h1>

        <div style={isValid ? styles.resultBoxValid : styles.resultBoxInvalid}>
          {isValid ? (
            <FaCheckCircle style={styles.resultIcon} />
          ) : (
            <FaTimesCircle style={styles.resultIcon} />
          )}
          <h2>{isValid ? "Ürün Güvenlidir" : "Ürün Güvensizdir"}</h2>
          <p>{verifyResult?.message}</p>
        </div>

        <div style={{ marginBottom: 30 }}>
          {product ? (
            <>
              <p style={styles.infoBox}>
                <strong>Ürün ID:</strong> {product.product_id}
              </p>
              <p style={styles.infoBox}>
                <strong>Ad:</strong> {product.product_name}
              </p>
              <p style={styles.infoBox}>
                <strong>Menşei:</strong> {product.origin}
              </p>
              <p style={styles.infoBox}>
                <strong>Açıklama:</strong> {product.description || "-"}
              </p>
              <p style={styles.infoBox}>
                <strong>QR Kodu:</strong> {product.qr_code || "-"}
              </p>
            </>
          ) : (
            <p>Ürün bilgisi bulunamadı.</p>
          )}
        </div>

        <h2 style={styles.subtitle}>Blokzincir İşlemleri</h2>
        <div>
          {product?.blockchainTransactions?.length > 0 ? (
            product.blockchainTransactions
              .slice()
              .reverse()
              .map((tx) => (
                <div key={tx.id} style={styles.blockItem}>
                  <p>
                    <strong>ID:</strong> {tx.id}
                  </p>
                  <p>
                    <strong>Zaman:</strong> {tx.timestamp}
                  </p>
                  <p>
                    <strong>Veri:</strong> {tx.data}
                  </p>
                </div>
              ))
          ) : (
            <p>Henüz blokzincir işlemi bulunmuyor.</p>
          )}
        </div>

        {allowedRoles.includes(userRole) && (
          <>
            <h2 style={styles.subtitle}>Yeni Blok Ekle</h2>
            <form onSubmit={handleAddBlock} style={styles.form}>
              <label htmlFor="blockData" style={styles.label}>
                Veri:
              </label>
              <textarea
                id="blockData"
                rows={4}
                required
                value={blockData}
                onChange={(e) => setBlockData(e.target.value)}
                style={styles.textarea}
                disabled={addingBlock}
              />
              <button
                type="submit"
                disabled={addingBlock}
                style={styles.button}
              >
                <FaPlus style={{ marginRight: 6 }} /> Blok Ekle
              </button>
            </form>
          </>
        )}
      </div>
    </div>
  );
}

const styles = {
  pageWrapper: {
    fontFamily: "Arial, sans-serif",
    padding: "20px",
    background: "transparent",
    color: "#333",
    minHeight: "100vh",
  },
  container: {
    maxWidth: "900px",
    margin: "0 auto",
    padding: "20px",
  },
  title: {
    color: "#0E2148",
    marginBottom: "20px",
    fontSize: "28px",
    textAlign: "center",
  },
  subtitle: {
    color: "#0E2148",
    margin: "30px 0 15px",
  },
  resultBoxValid: {
    padding: "30px",
    textAlign: "center",
    fontSize: "20px",
    fontWeight: "bold",
    borderRadius: "12px",
    marginBottom: "30px",
    color: "#fff",
    background: "#4CAF50",
    border: "2px solid #388E3C",
    boxShadow: "0 4px 10px #0003",
  },
  resultBoxInvalid: {
    padding: "30px",
    textAlign: "center",
    fontSize: "20px",
    fontWeight: "bold",
    borderRadius: "12px",
    marginBottom: "30px",
    color: "#fff",
    background: "#F44336",
    border: "2px solid #C62828",
    boxShadow: "0 4px 10px #0003",
  },
  resultIcon: {
    fontSize: "60px",
    marginBottom: "20px",
  },
  infoBox: {
    marginBottom: "10px",
    padding: "10px",
    background: "#fff",
    borderRadius: "8px",
    boxShadow: "0 1px 5px #0002",
  },
  blockItem: {
    background: "#fff",
    padding: "15px",
    marginBottom: "10px",
    borderRadius: "8px",
    boxShadow: "0 1px 5px #0002",
    borderLeft: "5px solid #4CAF50",
  },
  form: {
    marginBottom: "30px",
    padding: "20px",
    background: "#fff",
    borderRadius: "8px",
    boxShadow: "0 1px 5px #0002",
    maxWidth: "100%",
  },
  label: {
    fontWeight: "bold",
    display: "block",
    marginBottom: "8px",
  },
  textarea: {
    width: "100%",
    marginBottom: "15px",
    padding: "10px",
    border: "1px solid #ccc",
    borderRadius: "6px",
    background: "#f9f9f9",
    resize: "vertical",
    fontSize: "14px",
  },
  button: {
    padding: "10px 20px",
    background: "#4CAF50",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    width: "100%",
  },
};
