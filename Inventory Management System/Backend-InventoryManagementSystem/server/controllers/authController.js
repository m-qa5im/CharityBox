import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import userModel from '../models/userModel.js';
import transporter from '../config/nodemailer.js';

// REGISTER
export const register = async (req, res) => {
    const { firstname, lastname, email, phoneno, password } = req.body;

    if (!firstname || !lastname || !email || !phoneno || !password) {
        return res.status(400).json({ Success: false, message: "Please fill all the fields" });
    }

    try {
        const existingUser = await userModel.findOne({ email });
        if (existingUser) {
            return res.status(409).json({ Success: false, message: "User already exists" });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const newUser = await userModel.create({
            firstname,
            lastname,
            email,
            phoneno,
            password: hashedPassword,
        });

        const token = jwt.sign({ id: newUser._id }, process.env.JWT_SECRET, { expiresIn: '7d' });

        res.cookie('token', token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000, // 7 days
        });

        const mailOptions = {
            from: {
                name: 'Inventory Management System',
                address: process.env.SENDER_EMAIL,
            },
            to: email,
            subject: 'Welcome to Inventory Management System',
            text: `Hello ${firstname} ${lastname},\n\nThank you for registering with us.\n\nBest regards,\nTeam Inventory Management System`,
        };

        await transporter.sendMail(mailOptions);

        return res.status(201).json({ Success: true, message: "Registration successful" });

    } catch (error) {
        console.error(error);
        return res.status(500).json({ Success: false, message: error.message });
    }
};

// LOGIN
export const login = async (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ Success: false, message: "Please fill all the fields" });
    }

    try {
        const user = await userModel.findOne({ email });
        if (!user) {
            return res.status(404).json({ Success: false, message: "User not found" });
        }

        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.status(401).json({ Success: false, message: "Invalid credentials" });
        }

        const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET, { expiresIn: '7d' });

        res.cookie('token', token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000, // 7 days
        });

        return res.status(200).json({ Success: true, message: "Login successful" });

    } catch (error) {
        console.error(error);
        return res.status(500).json({ Success: false, message: error.message });
    }
};

// LOGOUT
export const logout = async (req, res) => {
    try {
        res.clearCookie('token', {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
        });
        return res.status(200).json({ Success: true, message: "Logout successful" });
    } catch (error) {
        console.error(error);
        return res.status(500).json({ Success: false, message: error.message });
    }
};

// SEND VERIFY OTP
export const sendVerifyOtp = async (req, res) => {
    try {
        const { id: userId } = req.user;

        const user = await userModel.findById(userId);
        if (!user) {
            return res.status(404).json({ Success: false, message: "User not found" });
        }

        if (user.isAccountVerified) {
            return res.status(400).json({ Success: false, message: "Account already verified" });
        }

        const Otp = String(Math.floor(100000 + Math.random() * 900000));
        user.verifyOTP = Otp;
        user.verifyOTPExpireAt = Date.now() + 5 * 60 * 1000; // 5 minutes from now
        await user.save();

        const mailOptions = {
            from: {
                name: 'Inventory Management System',
                address: process.env.SENDER_EMAIL,
            },
            to: user.email,
            subject: 'Account Verification OTP',
            text: `Hello ${user.firstname} ${user.lastname},\n\nYour OTP for account verification is: ${Otp}\n\nThis OTP will expire in 5 minutes.\n\nBest regards,\nTeam Inventory Management System`,
        };

        await transporter.sendMail(mailOptions);

        res.json({ Success: true, message: "OTP sent to your email" });

    } catch (error) {
        console.error(error);
        res.status(500).json({ Success: false, message: error.message });
    }
};

// VERIFY EMAIL
export const verifyEmail = async (req, res) => {
    const { otp } = req.body;
    const { id: userId } = req.user;

    if (!userId || !otp) {
        return res.status(400).json({ Success: false, message: "Please provide OTP" });
    }

    try {
        const user = await userModel.findById(userId);
        if (!user) {
            return res.status(404).json({ Success: false, message: "User not found" });
        }

        if (user.verifyOTP !== otp) {
            return res.status(400).json({ Success: false, message: "Invalid OTP" });
        }

        if (user.verifyOTPExpireAt < Date.now()) {
            return res.status(400).json({ Success: false, message: "OTP expired" });
        }

        user.isAccountVerified = true;
        user.verifyOTP = '';
        user.verifyOTPExpireAt = 0;
        await user.save();

        return res.json({ Success: true, message: "Account verified successfully" });

    } catch (error) {
        console.error(error);
        return res.status(500).json({ Success: false, message: error.message });
    }
};


export const isAuthenticated = async (req, res) => {
    try{
        return res.json({ Success: true, message: "User is authenticated"});
    }
    catch (error) {
        res.status(500).json({ Success: false, message: error.message });
    }
};