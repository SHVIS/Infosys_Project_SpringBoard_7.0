import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { validateUser, getUserDetails } from "../../Services/LoginService";
import AppButton from "../Common/AppButton";
import AppInput from "../Common/AppInput";
import AppAlert from "../Common/AppAlert";
import { layoutStyles, loginStyles, buttonStyles } from "../../styles";
import { setSession } from "../../utils/storage";
import "../../DisplayView.css";
import logo from "../../assets/logo.png";

const SECURITY_FEATURES = [
    "Secure account access",
    "Protected customer information",
    "Reliable digital banking",
];

const LoginPage = () => {

    const navigate = useNavigate();

    const [errors, setErrors] = useState({});
    const [flag, setFlag] = useState(true);

    const [loginData, setLoginData] = useState({
        username: "",
        password: "",
    });

    const onChangeHandler = (e) => {

        const { name, value } = e.target;

        setFlag(true);

        setLoginData((prev) => ({ ...prev, [name]: value }));

        if (errors[name]) {
            setErrors((prev) => ({ ...prev, [name]: "" }));
        }
    };

    const validateLogin = (e) => {

        e.preventDefault();

        validateUser(loginData.username, loginData.password)
            .then((response) => {

                const role = response.data;

                if (role === "Admin" || role === "Customer") {

                    getUserDetails()
                        .then((userRes) => {

                            console.log(userRes.data);

                            setSession({
                                role,
                                username: userRes.data.username,
                                personalName: userRes.data.personalName,
                            });

                            navigate(role === "Admin" ? "/admin-menu" : "/customer-menu");

                        })
                        .catch((error) => {
                            console.log("User details error:", error);
                            setFlag(false);
                        });

                } else {

                    setFlag(false);

                }

            })
            .catch((error) => {
                console.log("Login error:", error);
                setFlag(false);
            });
    };

    const handleValidation = (e) => {

        e.preventDefault();

        let tempErrors = {};
        let isValid = true;

        if (!loginData.username.trim()) {
            tempErrors.username = "Username is required";
            isValid = false;
        }

        if (!loginData.password.trim()) {
            tempErrors.password = "Password is required";
            isValid = false;
        }

        setErrors(tempErrors);

        if (isValid) {
            validateLogin(e);
        }
    };

    const registerNewUser = () => navigate("/register");
    const [showPassword, setShowPassword] = useState(false);

    return (
        <div style={layoutStyles.pageShell}>

            {/* ================= HEADER ================= */}
            <header style={layoutStyles.simpleHeader}>

                <div style={layoutStyles.brandRow}>
                    <img src={logo} alt="FinCore Bank" style={layoutStyles.brandLogo} />
                    <div>
                        <div style={layoutStyles.brandTitle}>FinCore</div>
                        <div style={layoutStyles.brandSubtitle}>DIGITAL BANKING</div>
                    </div>
                </div>

                <div style={layoutStyles.secureSession}>
                    <span style={layoutStyles.secureDot} />
                    Secure session
                </div>

            </header>

            {/* ================= MAIN ================= */}
            <main style={layoutStyles.contentMain}>
                <div style={loginStyles.grid}>

                    {/* ================= LEFT PANEL ================= */}
                    <div style={loginStyles.promoPanel}>

                        <div style={{ position: "relative", zIndex: 2 }}>
                            <div style={loginStyles.promoLogoBadge}>
                                <img src={logo} alt="FinCore" style={loginStyles.promoLogoImg} />
                            </div>

                            <div style={loginStyles.promoEyebrow}>Secure Digital Banking Platform with Transaction Management System</div>

                            <h1 style={loginStyles.promoHeading}>
                                Banking made
                                <br />
                                simple and secure.
                            </h1>

                            <p style={loginStyles.promoText}>
                                Access your bank account
                                through a secure and reliable digital
                                banking experience.
                            </p>
                        </div>

                        <div style={loginStyles.promoFeatureList}>
                            {SECURITY_FEATURES.map((item, index) => (
                                <div
                                    key={item}
                                    style={loginStyles.promoFeatureRow(
                                        index === SECURITY_FEATURES.length - 1
                                    )}
                                >
                                    <span style={loginStyles.promoFeatureIcon}>✓</span>
                                    <span style={loginStyles.promoFeatureText}>{item}</span>
                                </div>
                            ))}
                        </div>

                        <div style={loginStyles.promoDecorationOuter} />
                        <div style={loginStyles.promoDecorationInner} />

                    </div>

                    {/* ================= LOGIN CARD ================= */}
                    <div style={loginStyles.authCard}>

                        <div style={loginStyles.authCardHeader}>
                            <div style={loginStyles.authCardEyebrow}>CUSTOMER ACCESS</div>
                            <h2 style={loginStyles.authCardTitle}>Welcome back</h2>
                            <p style={loginStyles.authCardSubtitle}>
                                Sign in to continue banking securely.
                            </p>
                        </div>

                        {!flag && (
                            <AppAlert variant="error">
                                Invalid username or password.
                            </AppAlert>
                        )}

                        <form onSubmit={handleValidation}>

                            <AppInput
                                label="Username"
                                name="username"
                                type="text"
                                placeholder="Enter your username"
                                value={loginData.username}
                                onChange={onChangeHandler}
                                error={errors.username}
                            />
                             <div className="d-flex bg-white gap-1">
                                 
                            <AppInput
                                label="Password"
                                name="password"
                                type={showPassword ? 'text' : 'password'}
                                placeholder="Enter your password"
                                value={loginData.password}
                                onChange={onChangeHandler}
                                error={errors.password}
                                wrapperStyle={{ marginBottom: "28px",width:"100%" }}
                            />
                            <div title="Show / Hide Password" className="m-auto p-2 btn border border-outline border-primary" onClick={()=>setShowPassword((prev) => !prev)}>
                                {showPassword ? '🙈' : '👀'} <br />
                            </div>
                            </div> 
                            <AppButton type="submit" fullWidth>
                                Sign in securely →
                            </AppButton>

                        </form>

                        <div style={loginStyles.footerRow}>
                            <span style={loginStyles.footerMuted}>
                                Don't have a FinCore account?
                            </span>

                            <button
                                type="button"
                                onClick={registerNewUser}
                                style={buttonStyles.link}
                            >
                                Create account
                            </button>
                        </div>

                        <div style={loginStyles.secureNote}>
                            🔒 Your banking session is protected by
                            Secure Digital Banking Platform with Transaction Management System  security controls.
                        </div>

                    </div>

                </div>
            </main>

            {/* ================= FOOTER ================= */}
            <footer style={layoutStyles.pageFooter}>
                © 2026 Secure Digital Banking Platform with Transaction Management System . Stronger Future.
            </footer>

        </div>
    );
};

export default LoginPage;
