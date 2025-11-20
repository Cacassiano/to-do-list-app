import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function MainPage() {
    const [token] = useState(localStorage.getItem("token"));
    const navigate = useNavigate();

    useEffect(() => {
        console.log(token)
        if (!token) {
            navigate("/login");
        }
    }, [token, navigate]);

    return (
        <div>
            <h1>Bem vindo a página principal</h1>
        </div>
    );
}