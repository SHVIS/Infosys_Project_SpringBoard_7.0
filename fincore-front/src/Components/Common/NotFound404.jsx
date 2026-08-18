import { useState,useEffect } from "react";
import { useNavigate } from "react-router-dom";

const NotFound404 = () => {
    const [count, setCount] = useState(8);
    const navigate = useNavigate();
  

 useEffect(() => {  // autoMoveHome 
  if (count <= 0) return navigate("/");
  const timer = setTimeout(() => setCount(count - 1), 1000);
  return () => clearTimeout(timer);
}, [count, navigate]);

    return (
        <div className="card p-5 mt-4 mx-auto">

            <div className="container text-center">

                <div className="mb-4">
                    <h1 
                        className="display-1 fw-bold"
                        style={{ color: "#dc3545" }}
                    >
                        404
                    </h1>

                    <h2 className="login-title">
                       Page Not Found
                    </h2>

                    <p className="login-subtitle mt-3">
                        Page Not Exists view this page.
                    </p>
                </div>
                <p className="lead">Moving To Home Page</p>
                <div className="display-2 fw-bold btn btn-danger transition">
                     In {count} ...
                </div>
                    <br />  
                <button
                    className="w-50 fin-btn fw-bold btn btn-secondary mt-3"
                    onClick={() => navigate("/")}
                >
                    Take Me Home
                </button>

            </div>

        </div>
    );
};

export default NotFound404;