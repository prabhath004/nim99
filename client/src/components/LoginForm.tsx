import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';

const LoginForm: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState<{ email?: string; password?: string; server?: string }>({});
  const [isLoading, setIsLoading] = useState(false);
  const history = useHistory();

  const validateEmail = (email: string): boolean => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    const currentErrors: { email?: string; password?: string } = {};

    if (!email) {
      currentErrors.email = 'Email is required';
    } else if (!validateEmail(email)) {
      currentErrors.email = 'Email is not valid';
    }

    if (!password) {
      currentErrors.password = 'Password is required';
    }

    setErrors(currentErrors);

    if (Object.keys(currentErrors).length === 0) {
      setIsLoading(true);
      try {
        const response = await fetch('/api/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
          throw new Error('Login failed. Please check your credentials.');
        }

        const jwt = await response.text();
        localStorage.setItem('jwt', jwt); // Store JWT in localStorage

        // On success, redirect to dashboard
        history.push('/dashboard');
      } catch (error: any) {
        setErrors((prevErrors) => ({ ...prevErrors, server: error.message }));
      } finally {
        setIsLoading(false);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="email">Email:</label>
        <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        {errors.email && <span className="error">{errors.email}</span>}
      </div>

      <div>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        {errors.password && <span className="error">{errors.password}</span>}
      </div>

      {errors.server && <div className="error">{errors.server}</div>}

      <button type="submit" disabled={isLoading}>
        {isLoading ? 'Logging in...' : 'Login'}
      </button>
    </form>
  );
};

export default LoginForm;
