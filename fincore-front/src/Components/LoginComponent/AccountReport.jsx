import React from 'react';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAccounts, deleteAccountByNumber } from '../../Services/AccountService';
import '../../DisplayView.css';
import Modal from "../Common/Modal";
import BackButton from "../Common/BackButton";
import { getRole } from '../../Services/LoginService';
const AccountReport = () => {
  const [accounts, setAccounts] = useState([]);
  let navigate = useNavigate();

  const setAccountData = () => {
    getAccounts().then((response) => {
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

 const removeAccount = (id) => {

    deleteAccountByNumber(id)
    .then(() => {

        setAccounts(
            accounts.filter(
                account => account.accountNumber !== id
            )
        );

    });

};

  const returnBack = () => { return navigate("/admin-menu");
  };


  const [modal, setModal] = useState({ open: false, title: "", message: "", type: "info" });
  console.log(accounts);
  return (
    <div className="page">

      <div className="page-header">
        <h2 className="page-title"> Account Report</h2>
        <p className="page-subtitle"> View and manage all registered Accounts.</p>
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
                <th>Action</th>
              </tr>
            </thead>
            <tbody>

              {
                accounts.length > 0 ?

                  accounts.map((account) => (

                    <tr key={account.accountNumber}>

                      <td>#{account.accountNumber}</td>
                      <td> {account.customerId}</td>
                      <td>{account.accountType}</td>
                      <td> ${account.balance}</td>
                      <td>
                        {
                          account.status === "A"? <span className="status status-success"> Approved </span>:
                          account.status === "P"? <span className="status status-warning"> Pending </span>:
                                                  <span className="status status-danger">Rejected</span>
                        }
                      </td>
                      <td>{account.accountOpenDate}</td>
                      <td>
                        <button
                          className="fin-btn"
                          onClick={() => removeAccount(account.accountNumber)}
                          style={{
                            background: "#EF4444",
                            width: "120px",
                            height: "40px",
                            fontSize: "14px"
                          }}
                        >
                          Delete
                        </button>

                      </td>

                    </tr>

                  ))

                  :

                  <tr>
                    <td
                      colSpan="9"
                      className="text-center p-3"
                      style={{ color: "#6B7280" }}
                    >
                      No Accounts found.
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

export default AccountReport;