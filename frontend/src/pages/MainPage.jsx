import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/AxiosService.js"

export default function MainPage() {
    const [token, ] = useState(localStorage.getItem("token"));
    const navigate = useNavigate();

    useEffect(() => {
        validate_token()
    }, [token, navigate]);

    async function validate_token() {
        try{
            if(!token) throw new Error("No token");;
            console.log(token)
            const res = await api.get("/auth/check", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            if(res.status != 200) throw new Error("Invalid token");
            
        }catch(e) {
            console.error(e)
            localStorage.setItem("token", null)
            navigate("/login")
        } 
    }

    return (
        <div>
            <h1>Bem vindo a página principal</h1>
        </div>
    );
}