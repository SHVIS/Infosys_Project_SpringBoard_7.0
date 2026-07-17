import axios from "axios";
const BaseURL='http://localhost:9797/fincore'
const LOGIN_URL=BaseURL+'/login';
const ROLE_URL=BaseURL+'/role';
const LOGOUT_URL = BaseURL+'/logout';
const USR_URL =BaseURL+ '/user';

export const registerNewUser=(user)=> {
        return axios.post(LOGIN_URL,user, {
            withCredentials: true
        });
    }
 export const validateUser=(userId,password)=> {
    return axios.get(`${LOGIN_URL}/${userId}/${password}`, {
        withCredentials: true  
    });
    }
export const getUserDetails=()=>{
    return axios.get(LOGIN_URL,{
        withCredentials: true
    });
    }
    export const getRole=()=>{
    return axios.get(ROLE_URL,{
        withCredentials: true
    });
    }
   
 
 
 export const getUserId=()=>{
    return axios.get(USR_URL,{
        withCredentials: true
    });
     }
 
 export const logoutUser=()=>{
    return axios.post(LOGOUT_URL,{
        withCredentials: true
    });
}