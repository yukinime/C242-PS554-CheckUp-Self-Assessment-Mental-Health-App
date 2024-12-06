  <h1>Check-Up API - Prediksi Kesehatan Mental</h1>
    <p>API untuk melakukan prediksi kesehatan mental menggunakan model Machine Learning berbasis Firebase untuk menyimpan hasil prediksi.</p>

  <h2>Daftar Isi</h2>
    <ul>
        <li><a href="#deskripsi">Deskripsi</a></li>
        <li><a href="#prasyarat">Prasyarat</a></li>
        <li><a href="#instalasi">Instalasi</a></li>
        <li><a href="#penggunaan">Penggunaan</a></li>
        <ul>
            <li><a href="#menjalankan-server">Menjalankan Server</a></li>
            <li><a href="#endpoint-api">Endpoint API</a></li>
        </ul>
        <li><a href="#contoh-penggunaan">Contoh Penggunaan</a></li>
        <li><a href="#kontribusi">Kontribusi</a></li>
        <li><a href="#lisensi">Lisensi</a></li>
    </ul>

  <h2 id="deskripsi">Deskripsi</h2>
    <p>API ini digunakan untuk memprediksi kesehatan mental berdasarkan jawaban yang diberikan oleh pengguna. Jawaban tersebut diproses menggunakan model Machine Learning yang telah dilatih sebelumnya. API ini juga menyimpan hasil prediksi ke dalam Firebase Firestore.</p>

  <h2 id="prasyarat">Prasyarat</h2>
    <p>Sebelum menjalankan API ini, pastikan Anda telah menginstal hal-hal berikut:</p>
    <ul>
        <li><strong>Node.js</strong> (versi 16 ke atas)</li>
        <li><strong>NPM</strong> (Node Package Manager)</li>
        <li><strong>Google Cloud Firestore</strong> dan <strong>Firebase Admin SDK</strong></li>
        <li><strong>Model Machine Learning</strong> yang diperlukan untuk prediksi</li>
    </ul>

  <h2 id="instalasi">Instalasi</h2>
    <ol>
        <li><strong>Clone Repository</strong>
            <pre>
git clone https://github.com/yukinime/C242-PS554-CheckUp-Self-Assessment-Mental-Health-App.git
cd C242-PS554-CheckUp-Self-Assessment-Mental-Health-App
            </pre>
        </li>
        <li><strong>Instalasi Dependencies</strong>
            <pre>
npm install
            </pre>
        </li>
        <li><strong>Konfigurasi Firebase</strong>
            <p>Buat sebuah <strong>service account</strong> di <a href="https://console.cloud.google.com/" target="_blank">Google Cloud Console</a> dan simpan file <code>serviceAccount.json</code> di direktori <code>config/</code> dalam proyek ini.</p>
            <p>Jangan lupa untuk menambahkan file <code>serviceAccount.json</code> ke dalam <code>.gitignore</code> untuk menjaga agar kredensial tidak terpublikasi.</p>
        </li>
        <li><strong>Set Environment Variables</strong>
            <p>Buat file <code>.env</code> di root proyek dan tambahkan variabel berikut:</p>
            <pre>
GOOGLE_APPLICATION_CREDENTIALS=./config/serviceAccount.json
PORT=3000
            </pre>
        </li>
    </ol>

  <h2 id="penggunaan">Penggunaan</h2>

  <h3 id="menjalankan-server">Menjalankan Server</h3>
    <p>Untuk menjalankan server, gunakan perintah berikut:</p>
    <pre>
npm run start:dev
    </pre>
    <p>Perintah ini akan menjalankan server dengan menggunakan <strong>Nodemon</strong>, yang akan otomatis me-restart server setiap kali Anda melakukan perubahan pada file.</p>
    <p>Server akan berjalan di <code>http://localhost:3000</code>.</p>

  <h3 id="endpoint-api">Endpoint API</h3>
    <p>Berikut adalah beberapa endpoint yang dapat Anda akses pada API ini.</p>

  <h4>1. <strong>POST /predict</strong></h4>
    <p>Endpoint ini digunakan untuk mengirimkan data jawaban dari pengguna dan menerima prediksi kesehatan mental.</p>
    <ul>
        <li><strong>URL</strong>: <code>/predict</code></li>
        <li><strong>Method</strong>: <code>POST</code></li>
        <li><strong>Body</strong>: Mengirimkan data dalam format JSON yang berisi jawaban pengguna.</li>
    </ul>

  <p><strong>Contoh Request Body</strong>:</p>
    <pre>
{
  "answers": [1, 2, 3, 4, 5, 1, 3, 2, 5, 4, 1, 3, 4, 5, 2, 3]
}
    </pre>

  <p><strong>Validasi</strong>: Jawaban harus berjumlah 16.</p>

   <p><strong>Response:</strong></p>
    <ul>
        <li><strong>Success</strong> (HTTP 200):
            <pre>
{
  "prediction": "Hasil prediksi kesehatan mental"
}
            </pre>
        </li>
        <li><strong>Error</strong> (HTTP 400/500):
            <pre>
{
  "error": "Jumlah jawaban tidak sesuai dengan jumlah pertanyaan yang diharapkan (16 jawaban)."
}
            </pre>
        </li>
        <li><strong>Error 500</strong>: Jika terjadi masalah saat memuat model atau menyimpan ke Firestore:
            <pre>
{
  "error": "Terjadi kesalahan dalam melakukan prediksi."
}
            </pre>
        </li>
    </ul>

  <h2 id="contoh-penggunaan">Contoh Penggunaan</h2>

  <h3>1. Menjalankan API di Postman atau CURL</h3>
  <p><strong>POST</strong> request ke <code>http://localhost:3000/predict</code></p>

  <p><strong>Body</strong> (JSON):</p>
    <pre>
{
  "answers": [1, 2, 3, 4, 5, 1, 3, 2, 5, 4, 1, 3, 4, 5, 2, 3]
}
    </pre>

   <p><strong>Response</strong>:</p>
    <pre>
{
  "prediction": "Kesehatan Mental: Sehat"
}
    </pre>

  <h3>2. Contoh Response Error (Jumlah Jawaban Tidak Sesuai)</h3>

  <p><strong>Request</strong>: (Body dengan jumlah jawaban kurang dari 16)</p>
    <pre>
{
  "answers": [1, 2, 3, 4, 5]
}
    </pre>

  <p><strong>Response</strong>:</p>
    <pre>
{
  "error": "Jumlah jawaban tidak sesuai dengan jumlah pertanyaan yang diharapkan (16 jawaban)."
}
    </pre>

  <h2 id="kontribusi">Kontribusi</h2>
    <p>Jika Anda ingin berkontribusi pada proyek ini, silakan fork repository ini dan buat pull request dengan perubahan yang diinginkan. Pastikan Anda mengikuti standar coding yang ada dan menulis tes untuk fitur baru yang Anda tambahkan.</p>

  <h2 id="lisensi">Lisensi</h2>
    <p>Proyek ini dilisensikan di bawah <strong>MIT License</strong>.</p>
</body>


