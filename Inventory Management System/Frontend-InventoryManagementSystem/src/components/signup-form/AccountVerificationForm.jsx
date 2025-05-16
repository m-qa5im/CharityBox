import React, { useState } from 'react';

const AccountVerificationForm = () => {
    const [formData, setFormData] = useState({
        email: '',
        code: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Verification Data:', formData);
    };

    const handleResendCode = () => {
        console.log('Resend verification code to:', formData.email);
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
                        alt="Verification Illustration"
                        className="img-fluid h-100 w-100"
                        style={{ objectFit: 'cover' }}
                    />
                </div>

                {/* Right Form */}
                <div className="col-md-6 p-5">
                    <div className="mb-4">
                        <div className="brand-icon"></div>
                        <h3 className="fw-bold">Verify Account</h3>
                        <p className="text-muted">Enter the code sent to your email</p>
                    </div>

                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label className="form-label">Email address</label>
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

                        <div className="mb-3">
                            <label className="form-label">Verification Code</label>
                            <input
                                name="code"
                                type="text"
                                className="form-control rounded-3"
                                placeholder="Enter the 6-digit code"
                                value={formData.code}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="d-flex justify-content-between mb-3">
                            <button
                                type="button"
                                onClick={handleResendCode}
                                className="btn btn-link p-0 small"
                            >
                                Resend Code
                            </button>
                        </div>

                        <button type="submit" className="btn btn-dark w-100 rounded-pill">Verify</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default AccountVerificationForm;
