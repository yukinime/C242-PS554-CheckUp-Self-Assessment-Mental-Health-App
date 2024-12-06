const express = require("express");
const bodyParser = require("body-parser");
const dotenv = require("dotenv");
const { loadModel } = require("./services/modelService");
const requestRoutes = require("./routes/questions"); // Import route questions
const predictRoutes = require("./routes/predict"); // Import route predict

dotenv.config();
const app = express();
const port = process.env.PORT || 3000;

// Import Firestore (db)
const db = require("./config/firebase"); // Mengimpor db secara langsung

app.use(bodyParser.json()); // Mem-parsing JSON body

// Menggunakan route untuk /questions
app.use("/questions", requestRoutes);

// Menggunakan route untuk /predict
app.use("/predict", predictRoutes);

// Menjalankan server setelah model berhasil dimuat
app.listen(port, async () => {
  console.log(`Server berjalan di http://localhost:${port}`);

  try {
    // Tidak perlu memanggil initFirebase, cukup pastikan db sudah tersedia
    console.log("Firebase berhasil diinisialisasi");

    // Memuat model
    await loadModel();

    console.log("Model berhasil dimuat");
  } catch (error) {
    console.error("Error saat inisialisasi Firebase atau memuat model:", error);
  }
});
