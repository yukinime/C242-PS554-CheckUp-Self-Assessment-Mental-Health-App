const admin = require("firebase-admin");
const serviceAccount = require("./serviceAccount.json");

// Inisialisasi Firebase Admin SDK
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

// Instance Firestore
const db = admin.firestore();

// Menyediakan db Firestore langsung
module.exports = db;
