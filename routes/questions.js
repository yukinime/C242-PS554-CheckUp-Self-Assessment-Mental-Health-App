const express = require("express");
const router = express.Router();

// Daftar pertanyaan
const questions = [
  "Apakah Anda merasa sedih atau cemas dalam kehidupan sehari-hari?",
  "Apakah Anda kesulitan tidur atau merasa terlalu banyak tidur?",
  "Apakah Anda merasa tidak ada harapan atau tujuan dalam hidup?",
  "Apakah Anda merasa lelah sepanjang waktu meskipun sudah tidur cukup?",
  "Apakah Anda sering merasa terisolasi atau sendirian?",
  "Apakah Anda merasa kesulitan untuk melakukan aktivitas yang biasanya Anda nikmati?",
  "Apakah Anda merasa putus asa atau tidak berdaya?",
  "Apakah Anda sering merasa gelisah atau cemas?",
  "Apakah Anda merasa sulit untuk berkonsentrasi?",
  "Apakah Anda merasa seperti dunia ini tidak adil terhadap Anda?",
  "Apakah Anda merasa ada yang salah dengan diri Anda atau kehidupan Anda?",
  "Apakah Anda merasa sangat mudah tersinggung atau marah?",
  "Apakah Anda merasa cemas tentang masa depan?",
  "Apakah Anda merasa ingin menarik diri dari orang lain?",
  "Apakah Anda merasa tidak berharga atau tidak berguna?",
  "Apakah Anda sering merasa tidak bersemangat untuk melakukan aktivitas yang biasa Anda nikmati?",
];

// Endpoint untuk mendapatkan 16 pertanyaan
router.get("/", (req, res) => {
  return res.json({ questions });
});

module.exports = router;
