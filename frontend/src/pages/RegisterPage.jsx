import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import api from  "../services/AxiosService.js"

export default function RegisterPage() {

    const [token, ] = useState(localStorage.getItem("token"));
    const navigate = useNavigate();

    useEffect(() => {
        validate_token()
    }, [token, navigate])
    
    async function validate_token() {
        try{
            if(!token) throw new Error("No token");;
        
            const res = await api.get("/auth/check", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            if(res.status != 200) throw new Error("Invalid token");
            navigate("/")
        }catch(e) {
            console.error(e)
            localStorage.setItem("token", null)
        } 
    }

    async function handle_submit(e) {

        e.preventDefault();
        // User register logic here
        const formData = new FormData(e.target);
        const password = formData.get('password');
        const confirm_password = formData.get('confirm');

        if (!formData.get('username') || !formData.get('email') || !password || !confirm_password) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        if (password !== confirm_password) {
            alert("As senhas não coincidem.");
            return;
        }
        
        const data = {
            username: formData.get('username'),
            email: formData.get('email'),
            password: password
        }

        const res = await api.post('/auth/register', data);

        if(res.status !== 201) {
            alert(`Erro ao registrar usuário: ${res.message}`);
            return;
        }

        const token = res.data.token
        localStorage.setItem("token", token)
        console.log(`token: ${token}`)
        navigate('/'); 
    }

    function handle_confirm_password(e) {
        const password = document.getElementById('ipassword').value;
        if(e.target.value.length === 0 || e.target.value === password) {
            e.target.classList.remove("border-red-500")
            e.target.classList.remove("focus:border-red-500")
            e.target.classList.remove("focus:ring-red-500")
            e.target.setCustomValidity("");
            return;
        }
        e.target.setCustomValidity("As senhas não coincidem.");
        e.target.classList.add("border-red-500")
        e.target.classList.add("focus:border-red-500")
        e.target.classList.add("focus:ring-red-500")
        e.target.reportValidity();
        
    }

    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-md">
                <h1 className="text-center text-3xl font-extrabold text-gray-900 mb-8">
                    Registre um novo usuário
                </h1>
                <h2 className="text-center text-xl text-gray-900 mb-8">
                    Se tiver uma conta <a className="text-blue-900 underline hover:text-blue-600 cursor-pointer" onClick={() => navigate("/login")}>Faça o Login</a>
                </h2>
                <form onSubmit={handle_submit} autoComplete="true" className="bg-white py-8 px-6 shadow rounded-lg sm:px-10" method="POST">
                <div className="mb-6">
                    <label htmlFor="iusername" className="block text-sm font-medium text-gray-700 mb-2">
                    Nome de Usuário
                    </label>
                    <input 
                    type="text" 
                    id="iusername" 
                    name="username" 
                    autoComplete="username" 
                    required 
                    className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200"
                    />
                </div>

                <div className="mb-6">
                    <label htmlFor="iemail" className="block text-sm font-medium text-gray-700 mb-2">
                    Email
                    </label>
                    <input 
                    type="email" 
                    name="email" 
                    id="iemail" 
                    autoComplete="email" 
                    required 
                    className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200"
                    />
                </div>

                <div className="mb-6">
                    <label htmlFor="ipassword" className="block text-sm font-medium text-gray-700 mb-2">
                    Senha
                    </label>
                    <input 
                    type="password" 
                    name="password" 
                    id="ipassword" 
                    autoComplete="new-password" 
                    min={8} 
                    required 
                    className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200"
                    />
                </div>

                <div className="mb-6">
                    <label htmlFor="iconfirm" className="block text-sm font-medium text-gray-700 mb-2">
                    Confirmar Senha
                    </label>
                    <input 
                    type="password" 
                    name="confirm" 
                    id="iconfirm" 
                    onInput={handle_confirm_password}
                    min={8} 
                    required 
                    className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200"
                    />
                </div>

                <button 
                    type="submit" 
                    className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition duration-200"
                >
                    Registrar
                </button>
                </form>
            </div>
        </div>
    );
}