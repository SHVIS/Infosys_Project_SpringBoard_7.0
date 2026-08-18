import React, { useEffect, useMemo, useState } from "react";
import { Container } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { getAccountsByCustomerId,getAccountIdsByCustomerIdAndType } from "../../Services/AccountService";
import { getCustomerByUsername } from "../../Services/CustomerService";
import { applyForLoan, getLoanById } from "../../Services/LoanService";
import { commonStyles, layoutStyles } from "../../styles";
import loanStyles from "../../styles/loanStyles";
import logo from "../../assets/logo.png";

const money = (value) => `₹${Number(value || 0).toLocaleString("en-IN", { maximumFractionDigits: 2 })}`;

const LoanApplication = () => {
    const { loanId } = useParams();
    const navigate = useNavigate();
    const [loan, setLoan] = useState(null);
    const [customer, setCustomer] = useState(null);
    const [accounts, setAccounts] = useState([]);
    const [accountNumber, setAccountNumber] = useState("");
    const [loanAmount, setLoanAmount] = useState("");
    const [loanTenure, setLoanTenure] = useState("");
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    useEffect(() => {
        const load = async () => {
            try {
                const [loanResponse, customerResponse] = await Promise.all([getLoanById(loanId), getCustomerByUsername()]);
                const scheme = loanResponse.data;
                const currentCustomer = customerResponse.data;
                setLoan(scheme);
                setCustomer(currentCustomer);
                setLoanAmount(scheme.loanAmount || "");
                setLoanTenure(scheme.totalTenure || "");
                const accountResponse = await getAccountsByCustomerId(currentCustomer.customerId);
                // const accountResponse = await getAccountIdsByCustomerIdAndType("Savings");
                const list = Array.isArray(accountResponse.data) ? accountResponse.data : [];
                setAccounts(list.filter((account) => account.status === "A" && account.accountType==="Savings")," \n",); //culprit
                 
               // setAccounts(list) ;  
                if (list.length === 1) setAccountNumber(String(list[0].accountNumber));
            } catch (e) {
                setError(e.response?.data?.message || "Unable to prepare the loan application.");
            } finally { setLoading(false); }
        };
        load();
    }, [loanId]);
    const preview = useMemo(() => {
        if (!loan || !loanAmount || !loanTenure) return null;
        const amount = Number(loanAmount);
        const months = Number(loanTenure) * 12;
        const rate = Number(loan.interestRate);
        if (!amount || !months || !rate) return null;
        const r = rate / 1200;
        const f = Math.pow(1 + r, months);
        const exactEmi = (amount * r * f) / (f - 1);
        const emi = Math.round(exactEmi);
        const total = emi * months;
        return { months, emi, interest: total - amount, total };
    }, [loan, loanAmount, loanTenure]);

    const submit = async (e) => {
        e.preventDefault();
        setError(""); setMessage("");
        if (!accountNumber) return setError("Select the account to link with this loan.");
        if (Number(loanAmount) < 100000) return setError("Minimum loan amount is ₹1,00,000.");
        setSaving(true);
        try {
            const response = await applyForLoan({
                loanId:loan.loanId,
                customerId: customer.customerId,
                accountNumber: Number(accountNumber),
                savingsAccountNumber:Number(accountNumber),
                loanAmount: Number(loanAmount),
                loanTenure: Number(loanTenure),
            });
           
            setMessage(`Application ${response.data.customerLoanId} submitted successfully.`);
            setTimeout(() => navigate("/loan-applications"), 900);
        } catch (e) {
            setError(e.response?.data?.message || "Unable to submit the application.");
        } finally { setSaving(false); }
    };

    if (loading) return <div style={loanStyles.page}>Preparing application...</div>;
    if (!loan) return <div style={loanStyles.page}><Container><div style={loanStyles.card}>Loan scheme not found.</div></Container></div>;

    return (
        <div style={loanStyles.page}>
            <div style={layoutStyles.dashboardHeader}><Container><div style={layoutStyles.dashboardHeaderRow}><div style={layoutStyles.dashboardBrandRow}><img src={logo} alt="FinCore Bank" style={layoutStyles.dashboardLogo} /><div><h2 style={layoutStyles.dashboardBrandTitle}>FinCore Bank</h2><small style={layoutStyles.dashboardBrandSubtitle}>Customer • Loan Application</small></div></div></div></Container></div>
            <Container style={{ paddingTop: "28px" }}>
                <div style={loanStyles.backRow}>
                    <button
                        type="button"
                        onClick={() => navigate("/customer-menu")}
                        style={loanStyles.backButton}
                    >
                        <span style={{ fontSize: "20px", lineHeight: "1", marginTop: "-2px" }}>‹</span>
                        <span>Return Back</span>
                    </button>
                </div>
                <div style={{ ...loanStyles.header, marginTop: "20px" }}><div><div style={commonStyles.eyebrow}>CUSTOMER PORTAL • APPLY</div><h1 style={loanStyles.title}>Apply for {loan.loanId}</h1><p style={loanStyles.subtitle}>Choose your linked account and submit the application for bank review.</p></div></div>
                {error && <div style={{ ...loanStyles.alert, background: "#FEF2F2", color: "#B91C1C", border: "1px solid #FECACA" }}>{error}</div>}
                {message && <div style={{ ...loanStyles.alert, background: "#ECFDF5", color: "#047857", border: "1px solid #A7F3D0" }}>{message}</div>}
                <div style={{ display: "grid", gridTemplateColumns: "minmax(0,1.4fr) minmax(280px,.8fr)", gap: "22px" }}>
                    <div style={loanStyles.card}>
                        <form onSubmit={submit}>
                            <div style={loanStyles.field}><label style={loanStyles.label}>Customer</label><input style={{ ...loanStyles.input, background: "#F8FAFC" }} value={`${customer?.customerName || ""} • ${customer?.customerId || ""}`} readOnly /></div>
                            <div style={loanStyles.field}><label style={loanStyles.label}>Loan Scheme</label><input style={{ ...loanStyles.input, background: "#F8FAFC" }} value={`${loan.loanId} • ${money(loan.loanAmount)}`} readOnly /></div>
                            <div style={loanStyles.field}><label style={loanStyles.label}>Account to Link</label><select style={loanStyles.input} value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)} required><option value="">Select account</option>{accounts.map((a) => <option key={a.accountNumber} value={a.accountNumber}>{a.accountNumber} • {a.accountType} • Balance {money(a.balance)}</option>)}</select></div>
                            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px" }}>
                                <div style={loanStyles.field}><label style={loanStyles.label}>Requested Amount</label><input style={loanStyles.input} type="number" min="100000" value={loanAmount} onChange={(e) => setLoanAmount(e.target.value)} required /></div>
                                <div style={loanStyles.field}><label style={loanStyles.label}>Tenure (Years)</label><input style={loanStyles.input} type="number" min="1" value={loanTenure} onChange={(e) => setLoanTenure(e.target.value)} required /></div>
                            </div>
                            <div style={loanStyles.buttonRow}><button style={loanStyles.primaryButton} disabled={saving}>{saving ? "Submitting..." : "Submit Loan Application"}</button><button type="button" style={loanStyles.secondaryButton} onClick={() => navigate("/loan-list")}>Cancel</button></div>
                        </form>
                    </div>
                    <div style={loanStyles.card}><div style={commonStyles.eyebrow}>ESTIMATED TERMS</div><h2 style={{ ...commonStyles.sectionTitle, marginTop: "6px" }}>Repayment preview</h2><p style={commonStyles.sectionSubtitle}>Final values are calculated again by the bank backend.</p><div style={loanStyles.summaryGrid}>{preview && <><div style={loanStyles.summary}><div style={loanStyles.summaryLabel}>Interest</div><div style={loanStyles.summaryValue}>{loan.interestRate}%</div></div><div style={loanStyles.summary}><div style={loanStyles.summaryLabel}>Monthly EMI</div><div style={loanStyles.summaryValue}>{money(preview.emi)}</div></div><div style={loanStyles.summary}><div style={loanStyles.summaryLabel}>Total Interest</div><div style={loanStyles.summaryValue}>{money(preview.interest)}</div></div><div style={loanStyles.summary}><div style={loanStyles.summaryLabel}>Total Payable</div><div style={loanStyles.summaryValue}>{money(preview.total)}</div></div></>}</div></div>
                </div>
            </Container>
        </div>
    );
};

export default LoanApplication;
