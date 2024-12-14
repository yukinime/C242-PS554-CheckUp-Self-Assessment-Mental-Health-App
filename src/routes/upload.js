const multer = require('multer');
const path = require('path');
const { bucket } = require('../config/firebase');

// Konfigurasi Multer untuk menyimpan file sementara di memori
const storage = multer.memoryStorage();

const fileFilter = (req, file, cb) => {
    const allowedTypes = ['image/jpeg', 'image/png'];
    if (!allowedTypes.includes(file.mimetype)) {
        return cb(new Error('Invalid file type. Only JPG and PNG are allowed.'));
    }
    cb(null, true);
};

const upload = multer({
    storage,
    fileFilter,
    limits: {
        fileSize: 5 * 1024 * 1024,
    },
});

// Fungsi untuk mengunggah file ke Firebase Storage
const uploadToCloudStorage = async (file, username) => {
    const sanitizedUsername = username.replace(/[^a-zA-Z0-9_]/g, '_');

    const fileName = `${sanitizedUsername}/${Date.now()}_${file.originalname}`;
    const blob = bucket.file(fileName);

    const stream = blob.createWriteStream({
        metadata: {
            contentType: file.mimetype,
        },
    });

    return new Promise((resolve, reject) => {
        stream.on('error', (err) => {
            reject(err);
        });

        stream.on('finish', () => {
            const publicUrl = `https://storage.googleapis.com/${bucket.name}/${blob.name}`;
            resolve(publicUrl);
        });

        stream.end(file.buffer);
    });
};

module.exports = { upload, uploadToCloudStorage };
