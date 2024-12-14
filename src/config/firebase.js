const admin = require('firebase-admin');
const serviceAccount = require('../../serviceAccount.json');

// Inisialisasi Firebase Admin SDK hanya untuk aplikasi 'checkup-app'
const checkupApp = admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  storageBucket: `${serviceAccount.project_id}.firebasestorage.app`,
});

// Mengakses Firestore dan Storage dari aplikasi khusus
const db = checkupApp.firestore();
const bucket = checkupApp.storage().bucket();

// Function untuk uji koneksi Firestore dengan 'db-checkup'
const testFirestoreConnection = async () => {
  try {
    const testDoc = await db.collection('test').doc('connection').get();

    if (testDoc.exists) {
      console.log('Firebase connection successful');
    } else {
      console.log('Document does not exist, Firestore connected successfully');
    }
  } catch (error) {
    console.error('Error connecting to Firebase Firestore:', error);
  }
};

// Uji koneksi Firestore
testFirestoreConnection();

module.exports = { db, bucket, admin};
