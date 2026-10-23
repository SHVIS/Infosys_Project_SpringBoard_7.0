import axios from "axios";
import { BASE_URL } from "../utils/constants";
const LOGIN_URL=BASE_URL+'/login';
const ROLE_URL=BASE_URL+'/role';
const LOGOUT_URL = BASE_URL+'/logout';
const USR_URL =BASE_URL+ '/user';

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