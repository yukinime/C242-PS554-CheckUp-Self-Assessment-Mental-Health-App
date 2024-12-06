const express = require("express");
const { predictMentalHealth } = require("../services/modelService");
const db = require("../config/firebase");

const router = express.Router();

// Cek tipe db
console.log("Tipe db:", db);

// Endpoint untuk prediksi
router.post("/", async (req, res) => {
  const answers = req.body.answers;

  if (answers.length !== 16) {
    return res.status(400).json({
      error: "Jumlah jawaban tidak sesuai dengan jumlah pertanyaan yang diharapkan (16 jawaban).",
    });
  }

  try {
    const prediction = await predictMentalHealth(answers);

    // Simpan hasil prediksi ke Firestore
    const resultRef = await db.collection("predictions").add({
      answers, // Jawaban pengguna
      ...prediction, // Hasil prediksi
      timestamp: new Date(), // Waktu prediksi
    });

    console.log("Hasil prediksi disimpan dengan ID:", resultRef.id);

    return res.json(prediction);
  } catch (error) {
    console.error("Prediksi gagal:", error);
    return res.status(500).json({
      error: "Terjadi kesalahan dalam melakukan prediksi.",
    });
  }
});

module.exports = router;
