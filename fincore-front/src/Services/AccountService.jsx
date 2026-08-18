import React from 'react'
import axios from "axios";

const AC_URL='http://localhost:9797/fincore/account';
const AINFO_URL='http://localhost:9797/fincore/account-info';
const BINFO_URL='http://localhost:9797/fincore/balance-info';
 const ID_URL='http://localhost:9797/fincore/account-id';
const LIST_URL='http://localhost:9797/fincore/id-list';
const ACS_URL='http://localhost:9797/fincore/accounts';
 
export const addAccount=(account) => {
    return axios.post(AC_URL,account , {
            withCredentials: true
        });
	}

  export const updateAccount=(account)=>{
    return axios.put(AC_URL,account, {
        withCredentials: true
    });
   }
 
   
    export const getAccountByNumber=(number)=> {
    return axios.get(`${AC_URL}/${number}`, {
        withCredentials: true
    });
    }
 
export const getAccounts=()=>{
    return axios.get(AC_URL,{
        withCredentials: true
    });
    }
 
export const deleteAccountByNumber=(number)=>{
    return axios.delete(`${AC_URL}/${number}`, {
        withCredentials: true
    });
    }
 
   
export const getAccountsByCustomerId=()=>{
    return axios.get(AINFO_URL, {
        withCredentials: true
    });
}
 
export const getBalanceByAccountNumber=(accountNumber)=>{
    return axios.get(`${BINFO_URL}/${accountNumber}`,{
        withCredentials: true
    });
}
 
 export const generateAccountNumber=()=> {
    return axios.get(ID_URL, {
        withCredentials: true
    });
    }
    export const getAccountIdsByCustomerId=()=>{
		 return axios.get(LIST_URL, {
        withCredentials: true
    });
	}
// additional function
export const getAccountIdsByCustomerIdAndType=(accountType)=>{
    return axios.get(`${LIST_URL}/${accountType}`, {
        withCredentials: true
    });
    }
   
export const getAccountsByType=(accountType)=>{
    return axios.get(`${ACS_URL}/${accountType}`, {
        withCredentials: true
    });
    }
   
export const getAccountsByCustomerIdAndType=(accountType)=>{
    return axios.get(`${AINFO_URL}/${accountType}`, {
        withCredentials: true
    });
    }

    export const getAllAccountNumbers = () => {
    return getAccounts().then((response) => {
        let data = response?.data;

        if (Array.isArray(data)) {
            // API already returned the account list.
        }
        else if (data && Array.isArray(data.content)) {
            data = data.content;
        }
        else if (data && Array.isArray(data.data)) {
            data = data.data;
        }
        else if (data && Array.isArray(data.accounts)) {
            data = data.accounts;
        }
        else if (data && Array.isArray(data.result)) {
            data = data.result;
        }
        else {
            data = [];
        }

        const accountNumbers = data
            .map((account) =>
                account?.accountNumber ??
                account?.accountNo ??
                account?.account_number ??
                account?.number ??
                account?.accountId
            )
            .filter((accountNumber) =>
                accountNumber !== null &&
                accountNumber !== undefined &&
                String(accountNumber).trim() !== ""
            );

        response.data = [...new Set(accountNumbers.map(String))];

        return response;
    });
};