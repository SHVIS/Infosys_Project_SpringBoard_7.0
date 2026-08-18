import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import LoginPage from './Components/LoginComponent/LoginPage';
import RegisterUser from './Components/LoginComponent/RegisterUser';
import AdminMenu from './Components/LoginComponent/AdminMenu';
import CustomerMenu from './Components/LoginComponent/CustomerMenu';
import CustomerEntry from './Components/LoginComponent/CustomerEntry';
import CustomerReport from './Components/LoginComponent/CustomerReport';
import PendingListCustomer from './Components/LoginComponent/PendingListCustomer';
import CustomerEdit from './Components/LoginComponent/CustomerEdit';
import TransactionEntry from './Components/AccountTransactionComponent/TransactionEntry';
import AccountEntry from './Components/AccountTransactionComponent/AccountEntry';
import CustomerAccountList from './Components/LoginComponent/CustomerAccountList';
import ProtectedRoute from './ProtectedRoute';
import UnauthorizedPage from './Components/Common/UnauthorizedPage';
import AccountReport from './Components/LoginComponent/AccountReport';
import TransactionReport from './Components/AccountTransactionComponent/TransactionReport';
import AdminTransactionReport from './Components/AccountTransactionComponent/AdminTransactionReport.jsx';
import AccountDetails from './Components/AccountTransactionComponent/AccountDetails';
import AccountList from './Components/AccountTransactionComponent/AccountList';
import NotFound404 from './Components/Common/NotFound404';


import LoanEntry from './Components/LoanComponent/LoanEntry';
import LoanList from './Components/LoanComponent/LoanList';
import LoanApplication from './Components/LoanComponent/LoanApplication';
import LoanApplications from './Components/LoanComponent/LoanApplications';
import LoanApplicationReview from './Components/LoanComponent/LoanApplicationReview';
import LoanRepayment from './Components/LoanComponent/LoanRepayment';

function App() {
  return (
    <div className="App">
      <BrowserRouter>

        <Routes>
         {/*  ========================= Public  Routes =========================*/}

          <Route path="/" element={<LoginPage />} />
          <Route path="/register" element={<RegisterUser />} />
          <Route path="/unauthorized" element={<UnauthorizedPage />} />
          <Route path="*" element={<NotFound404 />} />  {/* No Matched Routes */}


          {/*  ========================= Admin Routes  =========================*/}

          <Route
            element={<ProtectedRoute allowedRoles={["Admin"]} />}
          ><Route path="/admin-transaction-report" element={<AdminTransactionReport />}/>
            <Route path="/admin-menu" element={<AdminMenu />} />
            <Route path="/customer-edit/:cid/:pno" element={<CustomerEdit />} />
            <Route path="/pending-customer" element={<PendingListCustomer />} />

            <Route path="/account-add" element={<AccountEntry />} />
            <Route path="/account-repo" element={<AccountReport />} />


            {/* Admin Loan Part */}
            <Route path="/loan-add" element={<LoanEntry />} />
            <Route path="/loan-edit/:loanId" element={<LoanEntry />} />
          </Route>


     
          {/*  ========================= Customer Routes  =========================*/}
          <Route
            element={<ProtectedRoute allowedRoles={["Customer"]} />}
          >  <Route path="/customer-menu" element={<CustomerMenu />} />
            <Route path="/customer-req" element={<CustomerEntry />} />
     

            {/* Customer Transactions  */}

            <Route path="/transaction-entry/:tno" element={<TransactionEntry />} />
            <Route path="/transaction-report" element={<TransactionReport />} />

            {/* Customer Loan Part */}
            <Route path="/loan-apply/:loanId" element={<LoanApplication />} />
            <Route path="/loan-applications" element={<LoanApplications />} />
          </Route>

          {/*  ========================= Routes Common Menu  =========================*/}

          <Route element={<ProtectedRoute allowedRoles={["Customer", "Admin"]} />}>
            <Route path="/customer-repo" element={<CustomerReport />} />
          
            <Route path="/account-details/:accountNumber" element={<AccountDetails />} />
            {/* <Route path="/customer-account-repo" element={<CustomerAccountList />} /> */}
            <Route path="/loan-list" element={<LoanList />} />
            <Route path="/account-list" element={<AccountList />} />
          <Route path="/loan-application-review" element={<LoanApplicationReview />} />
          <Route path="/loan-repayment/:applicationId" element={<LoanRepayment />} />
         
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
