# Gunakan image Node.js resmi sebagai base image
FROM node:18-slim

# Direktori kerja di dalam container
WORKDIR /usr/src/app

# Menyalin package.json dan package-lock.json terlebih dahulu untuk menginstal dependencies
COPY package*.json ./

# Menginstal dependencies
RUN npm install --production

# Menyalin seluruh kode aplikasi ke dalam container
COPY . .

# Menyediakan port yang akan digunakan aplikasi
EXPOSE 8080

# Menjalankan aplikasi dengan perintah 'npm start' di dalam container
CMD ["npm", "run", "start"]
