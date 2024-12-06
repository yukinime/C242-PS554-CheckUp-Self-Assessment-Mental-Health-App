const tf = require("@tensorflow/tfjs-node");
const dotenv = require("dotenv");
dotenv.config();

let model; // Menyimpan referensi model

// Fungsi untuk memuat model
async function loadModel() {
  if (model) {
    return model; // Jika model sudah dimuat, tidak perlu dimuat ulang
  }

  try {
    console.log("Memuat model...");
    model = await tf.loadLayersModel(process.env.MODEL_URL); // Ambil URL model dari environment variable
    console.log("Model berhasil dimuat.");
    return model;
  } catch (error) {
    console.error("Gagal memuat model:", error);
    throw new Error("Model gagal dimuat");
  }
}

// Fungsi untuk mendapatkan kategori berdasarkan prediksi
function getCategory(predictedClass, severity) {
  if (predictedClass === "Normal") {
    return "Normal";
  } else if (predictedClass === "Depression") {
    return severity >= 80 ? "Depresi Parah" : "Depresi Sedang";
  } else if (predictedClass === "Bipolar Type-1") {
    return severity >= 80 ? "Bipolar Type-1 (Mania Parah)" : "Bipolar Type-1 (Mania Ringan)";
  } else if (predictedClass === "Bipolar Type-2") {
    return severity >= 80 ? "Bipolar Type-2 (Hipomania Parah)" : "Bipolar Type-2 (Hipomania Ringan)";
  } else {
    return "Tidak Diketahui";
  }
}

// Fungsi untuk melakukan prediksi berdasarkan jawaban
async function predictMentalHealth(answers) {
  if (!model) {
    throw new Error("Model belum dimuat.");
  }

  // Menghitung jumlah setiap kategori
  const countSering = answers.filter((answer) => answer === "Sering").length;
  const countKadang = answers.filter((answer) => answer === "Kadang").length;
  const countTidakPernah = answers.filter((answer) => answer === "Tidak Pernah").length;

  // Logika Dominasi Jawaban
  if (countSering > countKadang && countSering > countTidakPernah) {
    return {
      predictedClass: "Depression",
      category: "Depresi Parah",
      severity: "100.00",
      definition: getDefinition("Depression"),
      suggestion: "Cobalah untuk mencari dukungan profesional.",
    };
  } else if (countKadang > countSering && countKadang > countTidakPernah) {
    return {
      predictedClass: "Bipolar Type-2",
      category: "Bipolar Type-2 (Gejala Sedang)",
      severity: "50.00",
      definition: getDefinition("Bipolar Type-2"),
      suggestion: "Pertimbangkan untuk berkonsultasi dengan profesional kesehatan mental.",
    };
  } else if (countTidakPernah > countSering && countTidakPernah > countKadang) {
    return {
      predictedClass: "Normal",
      category: "Normal",
      severity: "0.00",
      definition: getDefinition("Normal"),
      suggestion: "Anda tampaknya tidak menunjukkan gejala gangguan mental yang signifikan. Tetap jaga kesejahteraan mental Anda dengan rutinitas sehat.",
    };
  }

  // Jika tidak ada dominasi jelas, gunakan model AI untuk prediksi
  const mappedAnswers = answers.map((answer) => {
    if (answer === "Sering") return 1;
    if (answer === "Kadang") return 0.3;
    return 0; // "Tidak Pernah"
  });

  const inputTensor = tf.tensor([mappedAnswers]);
  const prediction = model.predict(inputTensor);
  const result = prediction.dataSync(); // Skor numerik dari hasil prediksi

  // Identifikasi skor tertinggi
  const maxScore = Math.max(...result);
  const predictedClassIndex = result.indexOf(maxScore);
  const predictedClass = ["Normal", "Depression", "Bipolar Type-1", "Bipolar Type-2"][predictedClassIndex];

  // Hitung severity berdasarkan skor
  const severity = (maxScore * 100).toFixed(2);

  return {
    predictedClass,
    category: getCategory(predictedClass, severity),
    severity,
    definition: getDefinition(predictedClass),
    suggestion: predictedClass === "Depression" ? "Cobalah untuk mencari dukungan profesional." : "Tetap semangat! Anda mampu menghadapi tantangan ini.",
  };
}

// Fungsi untuk mendapatkan definisi berdasarkan prediksi
function getDefinition(predictedClass) {
  switch (predictedClass) {
    case "Depression":
      return "Depresi adalah gangguan mood yang menyebabkan perasaan sedih atau kehilangan minat pada aktivitas yang biasanya menyenangkan.";
    case "Bipolar Type-1":
      return "Bipolar Type-1: Gangguan mood yang melibatkan periode depresi parah dan episode mania.";
    case "Bipolar Type-2":
      return "Bipolar Type-2: Gangguan mood yang melibatkan periode depresi parah dan episode hipomania.";
    default:
      return "Tidak ada gejala yang cukup untuk menunjukkan gangguan mental.";
  }
}

module.exports = { loadModel, predictMentalHealth };
