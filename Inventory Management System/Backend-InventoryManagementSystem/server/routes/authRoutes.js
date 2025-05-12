import express from 'express';
import { logout, register } from '../controllers/authController';
import { login } from '../controllers/authController';

const authRouter = express.Router();

authRouter.post('/register', register);
authRouter.post('/login', login);
authRouter.post('/logout', logout);
