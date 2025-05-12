import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import cookieParser from 'cookie-parser';
import connectDB from './config/mongodb.js';
import authRouter from './routes/authRoutes.js';

const app = express();
const PORT = process.env.PORT || 5000;
connectDB(); // Connect to MongoDB

app.use(express.json());
app.use(cookieParser());
app.use(cors({credentials: true})); // Allow requests from the frontend

// Routes: API endpoints
app.get('/', (req, res) => { 
    res.send('API...');
});
app.use('/api/auth', require('./routes/authRoutes.js')); // Authentication routes

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
