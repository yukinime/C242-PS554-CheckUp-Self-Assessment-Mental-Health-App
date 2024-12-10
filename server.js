const express = require('express');
const axios = require('axios');
const bodyParser = require('body-parser');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT||3000;

app.use(bodyParser.urlencoded({ extended: true }));

// Ambil API Key dan Search Engine ID dari environment
const GOOGLE_API_KEY = process.env.GOOGLE_API_KEY;
const SEARCH_ENGINE_ID = process.env.SEARCH_ENGINE_ID;

// Endpoint untuk artikel berdasarkan hasil kesehatan mental
app.post('/api/articles', async (req, res) => {
    const result = req.body.result;
    if (!result) {
        return res.status(400).json({ success: false, message: 'Parameter "result" diperlukan!' });
    }

    // Tentukan query berdasarkan hasil
    let query;
    switch (result.toLowerCase()) {
        case 'depresi':
            query = 'cara mengatasi depresi';
            break;
        case 'bipolar':
            query = 'penanganan bipolar';
            break;
        case 'normal':
            query = 'tips menjaga kesehatan mental';
            break;
        default:
            return res.status(400).json({ success: false, message: 'Hasil tidak valid! Gunakan depresi, bipolar, atau normal.' });
    }

    const url = `https://www.googleapis.com/customsearch/v1?q=${encodeURIComponent(query)}&key=${GOOGLE_API_KEY}&cx=${SEARCH_ENGINE_ID}`;

    try {
        const response = await axios.get(url);
        const articles = response.data.items.map(item => ({
            title: item.title,
            link: item.link,
            snippet: item.snippet,
        }));
        res.json({ success: true, data: articles });
    } catch (error) {
        console.error('Error fetching articles:', error);
        res.status(500).json({ success: false, message: 'Error fetching articles from Google API.' });
    }
});

// Jalankan server
app.listen(PORT, () => {
    console.log(`Server API berjalan di http://localhost:${PORT}`);
});
