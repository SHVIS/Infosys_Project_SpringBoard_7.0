import { useState,useEffect } from "react";
import { useNavigate } from "react-router-dom";

const UnauthorizedPage = () => {
    const [count, setCount] = useState(8);
    const navigate = useNavigate();
  

 useEffect(() => {  // autoMoveHome 
  if (count <= 0) return navigate("/");
  const timer = setTimeout(() => setCount(count - 1), 1000);
  return () => clearTimeout(timer);
}, [count, navigate]);

    return (
        <div className="card container p-5 mt-4 mx-auto">

            <div className="text-center">

                <div className="mb-4">
                    <h1 
                        className="display-1 fw-bold"
                        style={{ color: "#dc3545" }}
                    >
                        403
                    </h1>

                    <h2 className="login-title">
                        Unauthorized Access
                    </h2>

                    <p className="login-subtitle mt-3">
                        Sorry, you do not have permission to view this page.
                    </p>
                </div>


                <div 
                    className="alert alert-danger"
                    role="alert"
                >
                    <strong>Access Denied!</strong>
                    <br />
                    Your account does not have the required privileges.
                </div>


                <button
                    className="fin-btn btn btn-primary mt-3"
                    onClick={() => navigate("/")}
                >
                    Take Me Home (In {count} ...)
                </button>

            </div>

        </div>
    );
};

export default UnauthorizedPage;