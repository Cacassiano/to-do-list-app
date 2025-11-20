import {Routes, Route, useNavigate, Navigate} from "react-router-dom";
import LoginPage from "./pages/LoginPage.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";


function App() {
  const navigate = useNavigate();
  return (
    <Routes>
      <Route path='/login' element={<LoginPage />}/>
      <Route path='/register' element= {<RegisterPage /> } />
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>  
  );
  
}

export default App
