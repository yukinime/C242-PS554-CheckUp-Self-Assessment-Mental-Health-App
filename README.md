<body>
  <h1>API Documentation</h1>

   <h2>Installation</h2>
    <ol>
        <li>Clone this repository to your local machine:</li>
        <pre><code>git clone https://github.com/Hasanmudzakir4/api-articles.git</code></pre>

  <li>Navigate to the project folder:</li>
        <pre><code>cd repository-name</code></pre>

  <li>Install dependencies:</li>
        <pre><code>npm install</code></pre>

  <li>Copy the <code>.env.example</code> file to <code>.env</code> and update it with your Google API credentials:</li>
        <pre><code>cp .env.example .env</code></pre>

   <li>Run the server:</li>
        <pre><code>npm start</code></pre>
        <p>The server will be available at <strong>http://localhost:3000</strong>.</p>
    </ol>
  <h2>.env.example</h2>
    <p>Please make sure to add your API key and search engine ID in a <code>.env</code> file. Here is an example of how it should look:</p>
    <pre><code>
GOOGLE_API_KEY=AIzaSyAfUNrlb2rMcboaq0-6fgFACvhie4oK47s
SEARCH_ENGINE_ID=c7dbea4b62add4aa1
PORT=3000
    </code></pre>

  <h2>Dependencies</h2>
    <p>This project requires the following dependencies:</p>
    <ul>
        <li><strong>express</strong>: Web framework for Node.js.</li>
        <li><strong>axios</strong>: Promise-based HTTP client for making API requests.</li>
        <li><strong>body-parser</strong>: Middleware for parsing request bodies.</li>
        <li><strong>dotenv</strong>: Loads environment variables from a .env file.</li>
    </ul>

  <p>To install the required dependencies, run the following command:</p>
    <pre><code>npm install</code></pre>

  <h2>API Endpoints</h2>

  <h3>POST /api/articles</h3>
    <p>This endpoint allows you to retrieve articles based on a mental health result (<code>depresi</code>, <code>bipolar</code>, or <code>normal</code>).</p>

   <h4>Request Body</h4>
    <pre><code>
{
  "result": "depresi"
}
    </code></pre>
    <p><strong>result</strong>: One of the following values - <code>depresi</code>, <code>bipolar</code>, or <code>normal</code>. This parameter determines the type of mental health articles to search for.</p>

   <h4>Response</h4>
    <p>If successful, the response will return a list of articles:</p>
    <pre><code>
{
  "success": true,
  "data": [
    {
      "title": "Article Title",
      "link": "https://example.com/article",
      "snippet": "Short description of the article"
    }
  ]
}
    </code></pre>

  <p>If there is an error, the response will look like this:</p>
    <pre><code>
{
  "success": false,
  "message": "Error message"
}
    </code></pre>

  <h4>Error Handling</h4>
    <ul>
        <li>If <code>result</code> is missing, the response will be:
            <pre><code>
{
  "success": false,
  "message": "Parameter 'result' diperlukan!"
}
            </code></pre>
        </li>
        <li>If an invalid <code>result</code> value is provided, the response will be:
            <pre><code>
{
  "success": false,
  "message": "Hasil tidak valid! Gunakan depresi, bipolar, atau normal."
}
            </code></pre>
        </li>
    </ul>

   <h2>Testing API Endpoints with Postman</h2>

  <h3>Step 1: Import the Collection</h3>
    <ol>
        <li>Open Postman and click on <strong>Import</strong> in the top left.</li>
        <li>Choose <strong>Raw Text</strong> and paste the following:</li>
        <pre><code>
{
  "info": {
    "name": "Mental Health API",
    "description": "Test the mental health articles API",
    "_postman_id": "example-id",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get Articles",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"result\": \"depresi\"\n}"
        },
        "url": {
          "raw": "http://localhost:3000/api/articles",
          "protocol": "http",
          "host": [
            "localhost"
          ],
          "port": "3000",
          "path": [
            "api",
            "articles"
          ]
        }
      }
    }
  ]
}
        </code></pre>
        <li>Click <strong>Continue</strong> and then <strong>Import</strong>.</li>
    </ol>

  <h3>Step 2: Send Request</h3>
    <ol>
        <li>Select the "Get Articles" request from the collection.</li>
        <li>Modify the <code>result</code> in the body to either <code>depresi</code>, <code>bipolar</code>, or <code>normal</code>.</li>
        <li>Click <strong>Send</strong> and check the response.</li>
    </ol>

  <h2>Testing with PostWager</h2>
    <ol>
        <li>Download and install <a href="https://github.com/soumilshah1995/PostWager" target="_blank">PostWager</a>.</li>
        <li>Add your API endpoint (<code>http://localhost:3000/api/articles</code>) and the necessary JSON body with <code>result</code>.</li>
        <li>Test the response similarly to Postman.</li>
    </ol>


</body>
</html>
