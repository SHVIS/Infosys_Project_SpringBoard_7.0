import React, { useEffect,useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { validateUser, getUserDetails } from "../../Services/LoginService";
import '../../DisplayView.css';
import logo from "../../assets/logo.png";
const LoginPage = () => {



    let navigate = useNavigate();

   useEffect(() =>{checkStoredUser()}, []);

    const [errors, setErrors] = useState({});
    const [flag, setFlag] = useState(true);

    const [loginData, setLoginData] = useState({
        username: "",
        password: ""
    });

  const checkStoredUser = () => {

    const storedUser = localStorage.getItem("user");

    if (!storedUser) return; 

    try {
        const user = JSON.parse(storedUser);
        if (user.role === "Admin")  navigate("/admin-menu");
        if (user.role === "Customer") navigate("/customer-menu");
    } catch (error) {
        console.error("Invalid user data in storage");
        localStorage.removeItem("user");
    }
};
    const validateLogin = (e) => {

        e.preventDefault();

        validateUser(loginData.username, loginData.password)
            .then((response) => {

                const role = response.data;

                if (role === "Admin" || role === "Customer") {

                    getUserDetails().then((userRes) => {

                        console.log(userRes.data);
                    const user = {
                            username: userRes.data.username,
                            personalName: userRes.data.personalName,
                            role: role
                        };
                          // Store complete logged-in user
                        localStorage.setItem("user",JSON.stringify(user));
                        checkStoredUser();

                        /*     sessionStorage.setItem("username", response.data.username);
                          sessionStorage.setItem("role", response.data.role);*/

                    });

                }
                else setFlag(false);

            })
            .catch(() => setFlag(false));

    };
   
const onChangeHandler = (event) => {
    setFlag(true);
    const { name, value } = event.target;
    setLoginData(values => ({...values,[name]: value}));
};
    const handleValidation = (event) => {
        event.preventDefault();
        let tempErrors = {};
        let isValid = true;

        if (!loginData.username.trim()) {
            tempErrors.username = "User Name is required";
            isValid = false;
        }

        if (!loginData.password.trim()) {
            tempErrors.password = "Password is required";
            isValid = false;
        }

        setErrors(tempErrors);
        if (isValid) {
            validateLogin(event);
        }
    };
    const registerNewUser = (e) => {
        navigate('/register');
    }
    return (
        <div className="login-container">

            <div className="login-card">

                {/* Logo */}

                <div className="d-flex justify-content-center align-items-center mb-4">

                    <img
                        src={logo}
                        alt="FinCore Bank"
                        className="img-fluid"
                        style={{ width: "150px", height: "150px", objectFit: "contain" }}
                    />

                </div>

                {/* Heading */}

                <h1 className="login-title text-center mb-2">
                    Welcome Back
                </h1>

                <p className="login-subtitle text-center mb-4">
                    Sign in to continue banking securely.
                </p>

                <form>

                    <div className="mb-3">

                        <label className="form-label">
                            User Name
                        </label>

                        <input
                            type="text"
                            name="username"
                            className="fin-input form-control"
                            placeholder="Enter username"
                            value={loginData.username}
                            onChange={onChangeHandler}
                        />

                        {errors.username &&
                            <p className="error-text mb-0">
                                {errors.username}
                            </p>
                        }

                    </div>

                    <div className="mb-4">

                        <label className="form-label">
                            Password
                        </label>

                        <input
                            type="password"
                            name="password"
                            className="fin-input form-control"
                            placeholder="Enter password"
                            value={loginData.password}
                            onChange={onChangeHandler}
                        />

                        {errors.password &&
                            <p className="error-text mb-0">
                                {errors.password}
                            </p>
                        }

                    </div>

                    {!flag &&

                        <div className="alert alert-danger text-center fw-semibold mb-3">

                            Invalid Username or Password

                        </div>

                    }

                    <button
                        type="submit"
                        className="fin-btn btn btn-primary w-100"
                        style={{ height: "54px" }}
                        onClick={handleValidation}
                    >
                        Sign In
                    </button>

                </form>

                <button
                    type="button"
                    className="fin-btn-outline btn btn-outline-primary w-100 mt-3"
                    style={{ height: "54px" }}
                    onClick={registerNewUser}
                >
                    Create New Account
                </button>

                <div className="text-center text-muted small mt-4">
                    © 2026 FinCore Bank
                    <br />
                    Secure Banking. Stronger Future.
                </div>

            </div>

        </div>
    );
}
export default LoginPage;