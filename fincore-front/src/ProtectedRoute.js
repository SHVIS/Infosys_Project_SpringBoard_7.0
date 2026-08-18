import { Navigate, Outlet } from "react-router-dom";
import { getRole } from "./utils/storage";
import { STORAGE_KEYS } from "./utils/constants";   

const ProtectedRoute = ({ allowedRoles }) => {
const username = localStorage.getItem(STORAGE_KEYS.USERNAME);
const role = getRole();
const navigateUser = (role ==="Admin") ? "/admin-menu" :"/customer-menu";
     if (!username || !role) {
        return <Navigate to="/" replace />;
    }
    if (!allowedRoles.includes(role)) {
       <Navigate to="/unauthorized" replace />;
    }
    if (allowedRoles.includes(role)) {
        <Navigate to={navigateUser} replace />;
    }


    return <Outlet />;
};

export default ProtectedRoute;