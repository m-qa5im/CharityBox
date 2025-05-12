import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import userModel from '../models/userModel';


export const register = async (req, res) => {
    const { firstname, lastname, email, phoneno, password } = req.body;

    if (!firstname || !lastname || !email || !phoneno || !password) {
        return res.json({ Success: false, message: "Please fill all the fields" });
    }

    try {
        const existingUser = await userModel.findOne({ email });
        if (existingUser) {
            return res.json({ Success: false, message: "User already exists" });
        }
        const hashedPassword = await bcrypt.hash(password, 10);
        const newUser = await userModel.create({
            firstname,
            lastname,
            email,
            phoneno,
            password: hashedPassword,
        });
        await newUser.save();
        const token = jwt.sign({ id: newUser._id }, process.env.JWT_SECRET, { expiresIn: '7d' });
        res.cookie('token', token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000, // 7 days
        });
        return res.json({ Success: true, message: "Registration successful" });
    }catch (error) {
        console.error(error);
        return res.json({ Success: false, message: error.message });
    }
}


export const login = async (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.json({ Success: false, message: "Please fill all the fields" });
    }

    try {
        const user = await userModel.findOne({ email });
        if (!user) {
            return res.json({ Success: false, message: "User not found" });
        }
        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.json({ Success: false, message: "Invalid credentials" });
        }
        const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET, { expiresIn: '7d' });
        res.cookie('token', token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000, // 7 days
        });
        return res.json({ Success: true, message: "Login successful" });
    } catch (error) {
        console.error(error);
        return res.json({ Success: false, message: error.message });
    }
}

export const logout = async (req, res) => {
    try {
        res.clearCookie('token', {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
        });
        return res.json({ Success: true, message: "Logout successful" });
    } catch (error) {
        console.error(error);
        return res.json({ Success: false, message: error.message });
    }
}

