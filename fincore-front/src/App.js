import { BrowserRouter,Routes,Route } from 'react-router-dom';
import './App.css';
import LoginPage from './Components/LoginComponent/LoginPage';
import RegisterUser from './Components/LoginComponent/RegisterUser';
import AdminMenu from './Components/LoginComponent/AdminMenu';
import CustomerMenu from './Components/LoginComponent/CustomerMenu';

function App() {
  return (
    <div className="App">
     <BrowserRouter>
      <Routes>
      <Route path="/" element={<LoginPage/>}/>
      <Route path="/register" element={<RegisterUser/>}/>
      <Route path="/admin-menu" element={<AdminMenu/>}/>
      <Route path="/customer-menu" element={<CustomerMenu/>}/>
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
