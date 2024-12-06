<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
</head>
<body>
    <h1>Check-Up API - Mental Health Prediction</h1>
    <p>The Check-Up API is a web service built with <strong>Node.js</strong> that is used to predict mental health using a machine learning model and Firebase to store prediction results.</p>

  <h2>Table of Contents</h2>
    <ol>
        <li><a href="#description">Description</a></li>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#dependencies">Dependencies</a></li>
        <li><a href="#running-api">Running the API</a></li>
        <li><a href="#api-usage">API Usage</a></li>
            <ul>
                <li><a href="#endpoint">Endpoint</a></li>
                <li><a href="#usage-example">Usage Example</a></li>
            </ul>
        <li><a href="#testing-with-postman">Testing with Postman</a></li>
        <li><a href="#testing-with-swagger">Testing with Swagger</a></li>
        <li><a href="#license">License</a></li>
    </ol>

   <h2 id="description">Description</h2>
    <p>This API aims to provide predictions about mental health based on the answers provided by the user. The prediction is calculated using a <strong>Machine Learning</strong> model that has been pre-trained and is stored in <strong>Firebase Firestore</strong> for further record-keeping and analysis.</p>

   <h2 id="prerequisites">Prerequisites</h2>
    <p>Before running this API, make sure you have the following:</p>
    <ul>
        <li><strong>Node.js</strong> (v16 or newer)</li>
        <li><strong>NPM</strong> (Node Package Manager)</li>
        <li><strong>Firebase Project</strong>: You need a Firebase account and credentials to access Firestore.</li>
        <li><strong>Machine Learning Model</strong>: The model used to predict mental health must be available and loaded correctly.</li>
    </ul>

  <h2 id="installation">Installation</h2>
    <p>Follow these steps to install and run the API on your local machine:</p>
    <ol>
        <li><strong>Clone the Repository</strong>
            <pre><code>git clone https://github.com/username/check-up-api.git
cd check-up-api</code></pre>
        </li>
        <li><strong>Install Dependencies</strong>
            <p>After cloning the repository, run the following command to install all the required dependencies:</p>
            <pre><code>npm install</code></pre>
        </li>
        <li><strong>Configure Firebase</strong>
            <ul>
                <li>Create a project in <a href="https://console.firebase.google.com/">Firebase Console</a>.</li>
                <li>Download the <strong>serviceAccount.json</strong> credentials file and save it in the <strong>config/</strong> directory.</li>
                <li>Don't forget to add <strong>serviceAccount.json</strong> to the <strong>.gitignore</strong> to prevent this sensitive file from being uploaded to GitHub.</li>
            </ul>
        </li>
        <li><strong>Set Environment Variables</strong>
            <p>Create a <strong>.env</strong> file in the root of the project and add the following variables:</p>
            <pre><code>GOOGLE_APPLICATION_CREDENTIALS=./config/serviceAccount.json
PORT=3000</code></pre>
        </li>
    </ol>

   <h2 id="dependencies">Dependencies</h2>
    <p>This API uses several key dependencies to function properly:</p>
    <ul>
        <li><strong>express</strong>: Minimal web framework for Node.js.</li>
        <li><strong>firebase-admin</strong>: Firebase Admin SDK for interacting with Firebase Firestore.</li>
        <li><strong>nodemon</strong>: Tool for automatically restarting the server during development when file changes are detected.</li>
        <li><strong>tensorflow</strong>: Library for running machine learning models.</li>
    </ul>
    <p>For a complete list of dependencies, refer to the <strong>package.json</strong> file.</p>

   <h2 id="running-api">Running the API</h2>
   <p>Once you have installed all dependencies and configured the environment, run the API using the following command:</p>
    <pre><code>npm run start:dev</code></pre>
    <p>This will start the server at <strong>http://localhost:3000</strong>.</p>

  <h2 id="api-usage">API Usage</h2>

  <h3 id="endpoint">Endpoint</h3>
    <h4>1. <code>POST /predict</code></h4>
    <p>This endpoint is used to accept user answer data and provide a mental health prediction.</p>

   <p><strong>URL</strong>: <code>/predict</code></p>
    <p><strong>Method</strong>: <code>POST</code></p>

  <h5>Body (JSON)</h5>
    <pre><code>{
  "answers": [1, 2, 3, 4, 5, 1, 3, 2, 5, 4, 1, 3, 4, 5, 2, 3]
}</code></pre>

  <h5>Response (200)</h5>
    <pre><code>{
  "prediction": "Healthy"
}</code></pre>

  <h5>Response Error (400)</h5>
    <p>If the number of answers does not match the expected count (16 answers), you will receive the following error:</p>
    <pre><code>{
  "error": "The number of answers does not match the expected number of questions (16 answers)."
}</code></pre>

  <h2 id="usage-example">Usage Example</h2>

  <h3 id="testing-with-postman">Testing with Postman</h3>
    <p>To test the API using Postman, follow these steps:</p>
    <ol>
        <li>Open Postman.</li>
        <li>Select <strong>POST</strong> and enter the URL <code>http://localhost:3000/predict</code>.</li>
        <li>In the <strong>Body</strong> tab, choose <strong>raw</strong> and select <strong>JSON</strong> format.</li>
        <li>Enter the following JSON:
            <pre><code>{
  "answers": [1, 2, 3, 4, 5, 1, 3, 2, 5, 4, 1, 3, 4, 5, 2, 3]
}</code></pre>
        </li>
        <li>Click <strong>Send</strong> and observe the response from the API.</li>
    </ol>

   <h3 id="testing-with-swagger">Testing with Swagger</h3>
    <p>To test the API using Swagger, you can use the <code>swagger.json</code> file (if available) or integrate Swagger UI into your project. Follow these steps to add Swagger UI:</p>
    <ol>
        <li>Install Swagger UI:
            <pre><code>npm install swagger-ui-express</code></pre>
        </li>
        <li>Add Swagger in <code>app.js</code>:
            <pre><code>const swaggerUi = require('swagger-ui-express');
const swaggerDocument = require('./swagger.json');  // Ensure you have the swagger.json file

app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));</code></pre>
        </li>
        <li>Access Swagger UI via your browser at <code>http://localhost:3000/api-docs</code>.</li>
    </ol>

  <h2 id="license">License</h2>
    <p>This project is licensed under the <strong>MIT License</strong>.</p>
</body>
</html>
