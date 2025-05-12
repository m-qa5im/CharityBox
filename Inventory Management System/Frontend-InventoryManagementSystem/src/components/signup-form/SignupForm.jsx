import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const SignupForm = () => {
    const [formData, setFormData] = useState({
        first: '',
        last: '',
        email: '',
        phone: '',
        password: '',
        agree: false,
    });

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData({ ...formData, [name]: type === 'checkbox' ? checked : value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Form Data:', formData);
    };

    return (
        <div
            className="container bg-white rounded-4 shadow-lg overflow-hidden"
            style={{ maxWidth: '100%', width: '1080px', maxHeight: '95vh' }}
        >

            <div className="row g-0">

                {/* Left Image */}
                <div className="col-md-6 d-none d-md-block">
                    <img
                        src="/login-illustration.png"
                        alt="Register Illustration"
                        className="img-fluid h-100 w-100"
                        style={{ objectFit: 'cover' }}
                    />
                </div>

                {/* Right Form */}
                <div className="col-md-6 p-5">
                    <div className="mb-4">
                        <div className="brand-icon"></div>
                        <h3 className="fw-bold">Register</h3>
                        <p className="text-muted">Manage all your inventory efficiently</p>
                    </div>

                    <form onSubmit={handleSubmit}>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <label className="form-label">First name</label>
                                <input
                                    name="first"
                                    type="text"
                                    className="form-control rounded-3"
                                    placeholder="Enter your name"
                                    value={formData.first}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Last name</label>
                                <input
                                    name="last"
                                    type="text"
                                    className="form-control rounded-3"
                                    placeholder="minimum 8 characters"
                                    value={formData.last}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                        </div>

                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Email</label>
                                <input
                                    name="email"
                                    type="email"
                                    className="form-control rounded-3"
                                    placeholder="Enter your email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Phone no.</label>
                                <input
                                    name="phone"
                                    type="text"
                                    className="form-control rounded-3"
                                    placeholder="0300-0000000"
                                    pattern="[0-9]{4}-[0-9]{7}"
                                    value={formData.phone}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                        </div>

                        <div className="mb-3">
                            <label className="form-label">Password</label>
                            <input
                                name="password"
                                type="password"
                                className="form-control rounded-3"
                                placeholder="Enter your password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="form-check mb-3">
                            <input
                                type="checkbox"
                                name="agree"
                                className="form-check-input"
                                checked={formData.agree}
                                onChange={handleChange}
                                id="agree"
                            />
                            <label className="form-check-label" htmlFor="agree">
                                I agree to all terms, <a href="#">privacy policies</a>, and fees
                            </label>
                        </div>

                        <button type="submit" className="btn btn-dark w-100 rounded-pill">Sign up</button>
                    </form>

                    <p className="text-center mt-3 small">
                        Already have an account? <Link to="/">Log in</Link>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default SignupForm;
