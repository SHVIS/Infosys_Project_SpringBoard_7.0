import React from "react";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import {logoutUser} from '../../Services/LoginService';
import {useNavigate} from 'react-router-dom';
 
 
function CustomerMenu() {
    let navigate=useNavigate();
    const handleLogout = () => {
      logoutUser()
        .then(() => {
          localStorage.clear();
          sessionStorage.clear();
          navigate('/');
        })
    };
  return (
   <div className=".container">
   <br/>
    <div  align="center" style={{backgroundColor:'pink'}}>
      <h1 className = "text-center" style={{color:'magenta'}}><u><i>Bank Customer Menu</i></u></h1>
    </div>
    <Navbar expand="lg" bg="warning">
      <Navbar.Collapse id="basic-navbar-nav">
       <Nav className="me-auto">
     
             <NavDropdown title="Customer" id="collasible-nav-dropdown"><b>Customer</b>
               <NavDropdown.Item href="">Customer Request</NavDropdown.Item>
              <NavDropdown.Item href="">Customer  Report</NavDropdown.Item>
            </NavDropdown>
 
            <NavDropdown title="Account" id="collasible-nav-dropdown"><b>Account</b>
          <NavDropdown.Item href="">Account List</NavDropdown.Item>
          <NavDropdown.Item href="">Account Detail List</NavDropdown.Item>
          </NavDropdown>
 
            <NavDropdown title="Transaction" id="collasible-nav-dropdown"><b>Transaction</b>
               <NavDropdown.Item href="">Withdraw</NavDropdown.Item>
              <NavDropdown.Item href="">Deposit</NavDropdown.Item>
            </NavDropdown>
             
          <NavDropdown title="Loan" id="collasible-nav-dropdown"><b>Loan</b>
           <NavDropdown.Item href="">Loan List</NavDropdown.Item>
           
        </NavDropdown>
       
         
       
           <Nav.Link onClick={handleLogout}><b>Logout</b></Nav.Link>
     </Nav>
    </Navbar.Collapse>
  </Navbar>
</div>
 
  )
}

export default CustomerMenu