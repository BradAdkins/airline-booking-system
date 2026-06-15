const http = require('http');
const fs = require('fs').promises;

const server = http.createServer(async (req, res) => {
        try {
        const filePath = '.' + req.url;
    const content = await fs.readFile(filePath);
    res.writeHead(200, { 'Content-Type': 'image/jpeg' });
        res.end(content);
  } catch (err) {
        res.writeHead(404);
    res.end("File not found");
  }
          });

          server.listen(8000);


