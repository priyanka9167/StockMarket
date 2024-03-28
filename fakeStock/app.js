const express = require('express');
const faker = require('faker');
const _ = require('lodash');
const fs = require('fs');
const csvParser = require('csv-parser');
const { Client } = require('pg');
require('dotenv').config(); // Load environment variables from .env file


const app = express();
const port = 3000;
console.log(process.env.POSTGRES_USER,  process.env.POSTGRES_DB,  process.env.POSTGRES_PASSWORD)
// Configure PostgreSQL connection
const client = new Client({
  user: process.env.POSTGRES_USER,
  host: 'postgres',
  database: process.env.POSTGRES_DB,
  password: process.env.POSTGRES_PASSWORD,
  port: 5432,
});

// Connect to PostgreSQL
client.connect()
  .then(() => {
    console.log('Connected to PostgreSQL')
    createCompaniesTable();
  })
  .catch(err => console.error('Error connecting to PostgreSQL', err));

// Function to create the companies table
const createCompaniesTable = () => {
  const createTableQuery = `
    CREATE TABLE IF NOT EXISTS companies (
      id SERIAL PRIMARY KEY,
      symbol TEXT NOT NULL UNIQUE,
      name TEXT NOT NULL
    )
  `;

  client.query(createTableQuery)
      .then(() => {
        console.log('Companies table created successfully');
        // Once the table is created, insert data into it
        insertDataIntoDatabase();
      })
      .catch(err => console.error('Error creating companies table:', err));
};

// List of 5000 USA stock symbols (replace this with your list)
const usaStockSymbols = require('./StockName');

// Custom provider to generate random stock market data
faker.custom = {
  stockData: (symbol) => ({
    symbol: symbol,
    exchange: "NYSE", // Assuming all symbols are from NYSE
    mic_code: "XNYS", // MIC code for NYSE
    currency: "USD", // Assuming all are in USD
    datetime: faker.date.recent(),
    timestamp: faker.datatype.number(),
    open: faker.finance.amount(),
    high: faker.finance.amount(),
    low: faker.finance.amount(),
    close: faker.finance.amount(),
    volume: faker.datatype.number()
  })
};

// API endpoint to get stock market data for all symbols
app.get('/api/stock-data', (req, res) => {
  const stockData = usaStockSymbols.map(symbol => faker.custom.stockData(symbol));
  const response = {
    data: stockData,
    status: "ok"
  }
  console.log("API HITS")
  res.json(response);
  
});

// Function to read CSV file and insert data into PostgreSQL table
const insertDataIntoDatabase = () => {
  const csvFilePath = 'data/NASDAQ.csv'; // Path to your CSV file

  fs.createReadStream(csvFilePath, { encoding: 'utf8' })
    .pipe(csvParser({
      headers: false, // Disable header row parsing
      raw: true,
      from_line: 2 
    }))
    .on('data', (row) => {
      const symbol = row[0].toString('utf8');
      const name = row[1].toString('utf8');
      // Check if the row exists in the database
      const query = {
        text: 'SELECT COUNT(*) FROM companies WHERE symbol = $1',
        values: [symbol],
      };
      client.query(query)
        .then((result) => {
          // If the row does not exist, insert it into the database
          if (result.rows[0].count === '0') {
            const insertQuery = {
              text: 'INSERT INTO companies(symbol, name) VALUES($1, $2)',
              values: [symbol, name],
            };
            client.query(insertQuery)
              .then(() => console.log('Inserted row:', row))
              .catch(err => console.error('Error inserting row:', err.stack));
          } else {
            console.log('Row already exists:', symbol,name);
          }
        })
        .catch(err => console.error('Error checking row existence:', err));
    })
    .on('end', () => {
      console.log('CSV file successfully processed');
    });
};


app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});