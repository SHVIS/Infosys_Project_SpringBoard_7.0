import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../DisplayView.css';
import Modal from "../Common/Modal";
import BackButton from "../Common/BackButton";
import { getAccountsByCustomerId } from '../../Services/AccountService';

const CustomerAccountList = () => {
  const [accounts, setAccounts] = useState([]);
  let navigate = useNavigate();


  const setAccountData = () => {
    getAccountsByCustomerId().then((response) => {
      setAccounts(response.data);
    }).catch(error => {
      setModal({
        open: true,
        title: "Error",
        message: "Error Occurred while loading data : " + error,
        type: "error"
      });
    });
  }

  useEffect(() => {
    setAccountData();
  }, []);
  // const removeAccount = (id) => {
  //   deleteCustomerById(id).then(res => {
  //     let remainaccounts = account.filter((account) => (account.accountsNumber !== id));
  //     if(confirm("Are You Sure Want to Remove !"))
  //     setAccountData(remainaccounts);
  //   });
  //   navigate('/account-repo');
  // }

  const returnBack = () => {

    const role = localStorage.getItem("role");

    if (role === "Admin") {
      navigate("/admin-menu");
    } else {
      navigate("/customer-menu");
    }

  };


  const [modal, setModal] = useState({ open: false, title: "", message: "", type: "info" });

  return (
    <div className="page">

      <div className="page-header">
        <h2 className="page-title"> Customer Report</h2>
        <p className="page-subtitle"> View and manage all registered account.</p>
      </div>

      <div className="fin-card has-back-btn">

        <BackButton onClick={returnBack} />

        <div style={{ overflowX: "auto" }}>

          <table className="fin-table">
            <thead>
              <tr>
                <th>Account Number </th>
                <th>Customer ID</th>
                <th>Account Type</th>
                <th>Balance</th>
                <th>Status</th>
                <th>Opening Date</th>
              </tr>
            </thead>
            <tbody>

              {
                accounts.length > 0 ?
                  accounts.map((account) => (
                    <tr key={account.accountNumber}>
                      <td>#{account.customerId}</td>
                      <td>{account.accountType}</td>
                      <td>{account.balance}</td>


                      <td>
                        {
                          account.status === "A" ? <span className="status status-success"> Approved</span>
                            :
                            account.status === "P" ? <span className="status status-warning"> Pending </span>
                              : <span className="status status-danger"> Rejected </span>
                        }
                      </td>
                      <td>{account.dateOfJoin}</td>
                    </tr>
                  ))

                  :  /* Customer Not Found */

                  <tr>
                    <td colSpan="9" className='text-center p-3' style={{ color: "#6B7280" }}>
                      No account found.
                    </td>
                  </tr>
              }
            </tbody>
          </table>
        </div>
      </div>

      <Modal open={modal.open} title={modal.title} message={modal.message} type={modal.type} onClose={() => setModal({ ...modal, open: false })}
      />

    </div>
  );
}

export default CustomerAccountList;