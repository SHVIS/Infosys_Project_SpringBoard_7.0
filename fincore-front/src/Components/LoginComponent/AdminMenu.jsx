import React from 'react'
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import {logoutUser} from '../../Services/LoginService';
import {useNavigate} from 'react-router-dom';
function AdminMenu() {
    let navigate=useNavigate();
  const handleLogout = () => {
  logoutUser().then(() => {
          localStorage.clear();
          sessionStorage.clear();
          navigate('/');
     })
  };
  return (
   <div className=".container">
     <br/>
      <div  align="center" style={{backgroundColor:'green'}}>
      <h1 className = "text-center" style={{color:'burlywood'}}><u><i>Bank Admin Menu</i></u></h1>
      </div>
       <Navbar expand="lg" bg="warning">
         <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
           <NavDropdown title="Customer" id="collasible-nav-dropdown"><b>Customer</b>
              <NavDropdown.Item href="">Customer List</NavDropdown.Item>
              <NavDropdown.Item href="">Customer Addition</NavDropdown.Item>
              <NavDropdown.Item href="">Customer Wise Account Report</NavDropdown.Item>
              <NavDropdown.Item href="">Customer Wise Loan Report</NavDropdown.Item>
            </NavDropdown>
            <NavDropdown title="Account" id="collasible-nav-dropdown"><b>Account</b>
              <NavDropdown.Item href="">Account List</NavDropdown.Item>
              <NavDropdown.Item href="">Account Addition</NavDropdown.Item>
              <NavDropdown.Item href="">Credit Transactions</NavDropdown.Item>
              <NavDropdown.Item href="">Debit Transactions</NavDropdown.Item>
             </NavDropdown>
            <NavDropdown title="Loan" id="collasible-nav-dropdown"><b>Loan</b>
              <NavDropdown.Item href="">Loan List</NavDropdown.Item>
              <NavDropdown.Item href="">Loan Addition</NavDropdown.Item>
              <NavDropdown.Item href="">Customer Loan Approval</NavDropdown.Item>
             
            </NavDropdown>
            <NavDropdown title="Account" id="collasible-nav-dropdown"><b>Account</b>
              <NavDropdown.Item href="">Account Report</NavDropdown.Item>
              <NavDropdown.Item href="">Account Payment Report</NavDropdown.Item>
              <NavDropdown.Item href="">Customerwise Account Report</NavDropdown.Item>
             </NavDropdown>
           
            <Nav.Link onClick={handleLogout}><b>Logout</b></Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </div>
      );
}

export default AdminMenu