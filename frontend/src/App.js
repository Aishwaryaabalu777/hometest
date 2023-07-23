import logo from './logo.svg';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import SideNav from './components/sideNav/SideNav';
import Auth from './components/auth/Auth';
import Register from './components/auth/Register';
import Dashboard from './components/dashboard/Dashboard';

function App() {
  return (
   <Routes>
    <Route path='/' element={<Auth/>} />
    <Route path='/register' element={<Register/>} />
    <Route path='/dashboard' element={<Dashboard/>}/>
   
   </Routes>
  );
}

export default App;
