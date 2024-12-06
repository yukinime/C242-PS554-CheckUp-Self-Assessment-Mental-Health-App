// Array pesan motivasi khusus depresi
const depressionMessages = [
  "Anda tidak sendirian dalam perjuangan ini. Langkah pertama adalah mengakui perasaan Anda, dan langkah selanjutnya adalah mencari bantuan.",
  "Meskipun depresi bisa sangat menantang, Anda memiliki kemampuan untuk menghadapinya. Ada banyak orang yang peduli dan siap membantu Anda.",
  "Perjalanan menuju pemulihan dimulai dengan keberanian untuk mencari bantuan. Depresi adalah tantangan yang dapat Anda atasi.",
  "Jangan pernah merasa sendirian dalam perjalanan ini. Menghadapi depresi memang berat, tetapi Anda lebih kuat dari yang Anda kira.",
  "Tetap positif, langkah kecil menuju pemulihan dapat membuat perbedaan besar dalam hidup Anda.",
];

// Fungsi untuk mendapatkan pesan motivasi acak untuk depresi
function getDepressionMessage() {
  return depressionMessages[Math.floor(Math.random() * depressionMessages.length)];
}

module.exports = { getDepressionMessage };
