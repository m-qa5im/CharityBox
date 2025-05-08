import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage.jsx';
import SignupPage from './pages/SignupPage.jsx';


function App() {    
    return (
        <Router>
          <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
          </Routes>
        </Router>
      );
}
export default App;