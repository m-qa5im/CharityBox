import React from 'react';
import LoginForm from '../components/login-form/LoginForm.jsx';

const LoginPage = () => {
  return (
    <div
      className="bg-light overflow-hidden d-flex justify-content-center align-items-center min-vh-100 px-3"
    >
      <LoginForm />
    </div>
  );
};

export default LoginPage;
