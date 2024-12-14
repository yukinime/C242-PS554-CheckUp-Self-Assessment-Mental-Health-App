const express = require('express');
const { db } = require('../config/firebase');
const jwt = require('jsonwebtoken');
const { upload, uploadToCloudStorage } = require('../routes/upload');
const router = express.Router();

// Fungsi untuk mengonversi timestamp Firestore ke format tanggal
const convertFirestoreTimestampToDate = (timestamp) => {
    const date = new Date(timestamp._seconds * 1000 + timestamp._nanoseconds / 1000000);
    return date.toLocaleString(); // Menggunakan toLocaleString untuk format tanggal/waktu lokal
};

const verifyToken = (req, res, next) => {
    const token = req.headers['authorization']?.split(' ')[1];

    if (!token) {
        return res.status(403).json({ success: false, message: 'Access token is required' });
    }

    try {
        const decoded = jwt.verify(token, process.env.SECRET_KEY);
        req.userId = decoded.userId;
        next();
    } catch (error) {
        return res.status(401).json({ success: false, message: 'Invalid or expired token', error: error.message });
    }
};

// Get user profile
router.get('/currentuser', verifyToken, async (req, res) => {
    try {
        const userSnapshot = await db.collection('profiles').doc(req.userId).get();

        if (!userSnapshot.exists) {
            return res.status(404).json({ success: false, message: 'User profile not found' });
        }

        const userData = userSnapshot.data();

        // Konversi createdAt dan updatedAt menjadi format tanggal yang lebih mudah dibaca
        userData.createdAt = convertFirestoreTimestampToDate(userData.createdAt);
        userData.updatedAt = convertFirestoreTimestampToDate(userData.updatedAt);

        res.status(200).json({
            success: true,
            message: 'Profile fetched successfully',
            data: userData,
        });
    } catch (error) {
        console.error('Error fetching profile:', error);
        res.status(500).json({ success: false, message: 'Error fetching profile', error: error.message });
    }
});

// Update user profile
router.post('/currentuser', verifyToken, upload.single('profileImage'), async (req, res) => {
    const { username, birthPlace, birthDate, gender, email, phone, address } = req.body;
    const { userId } = req;

    try {
        let imageUrl;
        if (req.file) {
            // Upload image to Cloud Storage and get URL
            imageUrl = await uploadToCloudStorage(req.file, userId);
        }

        const userDoc = await db.collection('profiles').doc(userId).get();

        if (userDoc.exists) {
            const updateData = {
                username,
                birthPlace,
                birthDate,
                gender,
                email,
                phone,
                address,
                updatedAt: new Date(), // Updated timestamp
            };
            if (imageUrl) updateData.imageUrl = imageUrl;

            // Update profile in Firestore
            await db.collection('profiles').doc(userId).update(updateData);
            res.status(200).json({ success: true, message: 'Profile updated successfully' });
        } else {
            // Create profile if doesn't exist
            await db.collection('profiles').doc(userId).set({
                username,
                birthPlace,
                birthDate,
                gender,
                email,
                phone,
                address,
                imageUrl,
                createdAt: new Date(), // Created timestamp
            });
            res.status(201).json({ success: true, message: 'Profile created successfully' });
        }
    } catch (error) {
        console.error('Error saving profile:', error);
        res.status(500).json({ success: false, message: 'Failed to save profile', error: error.message });
    }
});

// Delete user profile
router.delete('/currentuser', verifyToken, async (req, res) => {
    const { userId } = req;

    try {
        const userDoc = await db.collection('profiles').doc(userId).get();

        if (!userDoc.exists) {
            return res.status(404).json({ success: false, message: 'User profile not found' });
        }

        // Delete profile document from Firestore
        await db.collection('profiles').doc(userId).delete();

        // If there is an image in Firebase Storage, delete it
        if (userDoc.data().imageUrl) {
            const fileName = userDoc.data().imageUrl.split('/').pop(); // Extract file name from URL
            const file = bucket.file(fileName);
            await file.delete(); // Delete the file from Firebase Storage
        }

        res.status(200).json({ success: true, message: 'Profile deleted successfully' });
    } catch (error) {
        console.error('Error deleting profile:', error);
        res.status(500).json({ success: false, message: 'Error deleting profile', error: error.message });
    }
});

module.exports = router;
