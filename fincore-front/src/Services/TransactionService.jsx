import axios from 'axios';
import { BASE_URL } from "../utils/constants";
// const TY_URL = BASE_URL+'/trans-type';const TAC_URL = BASE_URL+'/trans-ac';
const TINFO_URL = BASE_URL+'/trans-info';
const TR_URL = BASE_URL+'/trans';


// Generate Transaction ID
export const generateTransactionId = () => {
    return axios.get(TINFO_URL, {
        withCredentials: true
    });
};
// Get Transactions By Customer
export const getTransactionsByCustomer = (customerId) => {
    return axios.get(`${TR_URL}/customer/${customerId}`, {
        withCredentials: true
    });
};
// Get Transactions By Account
export const getTransactionsByAccount = (accountNumber) => {
    return axios.get(`${TR_URL}/account/${accountNumber}`, {
        withCredentials: true
    });
};
export const addTransaction = (transaction) => {
    return axios.post(TR_URL, transaction, {
        withCredentials: true
    });
};


export const getAllTransactions = () => {
    return axios.get(TR_URL, {
        withCredentials: true
    });
};


export const getTransactionById = (id) => {
    return axios.get(`${TR_URL}/${id}`, {
        withCredentials: true
    });
};


export const deleteTransactionById = (id) => {
    return axios.delete(`${TR_URL}/${id}`, {
        withCredentials: true
    });
};


// Get Transactions By Type
export const getTransactionsByType = (type) => {
    return axios.get(`${TR_URL}/type/${type}`, {
        withCredentials: true
    });
};

