import { useEffect, useState } from 'react';
import api from '../services/AxiosService.js'
import { useNavigate } from 'react-router-dom';

export default function Login_Page() {

    const [email, setEmail] = useState("");
    const [token, setToken] = useState(localStorage.getItem("token"));
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    
    useEffect(() => {
        validate_token()
    }, [token, navigate])

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
            navigate("/")
        }catch(e) {
            console.error(e)
            localStorage.setItem("token", null)
        } 
    }

    async function handle_submit(e) {
        e.preventDefault();

        const res = await api.post("/auth/login", JSON.stringify({email:email, password:password}));
        console.log("status da request: "+res.status)
        if(res.status == 201) {
            console.log("salvando o token no localstorage\n"+res.data.token)
            localStorage.setItem("token", res.data.token)
            
            navigate("/")
        }  
    }

    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-md">
                <h1 className="text-center text-3xl font-extrabold text-gray-900 mb-8">
                Login
                </h1>
                <h2 className="text-center text-xl text-gray-900 mb-8">
                    Se não tiver uma conta <a className="text-blue-900 underline hover:text-blue-600 cursor-pointer" onClick={() => navigate("/register")}>Registre-se</a>
                </h2>
                <form onSubmit={handle_submit} autoComplete="true" className="bg-white py-8 px-6 shadow rounded-lg sm:px-10">
                <div className="mb-6">
                    <label htmlFor="iemail" className="block text-sm font-medium text-gray-700 mb-2">
                    Email
                    </label>
                    <input 
                    type="email" 
                    name="email" 
                    id="iemail" 
                    onInput={(e) => setEmail(e.target.value)}
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
                    onInput={(e) => setPassword(e.target.value)}
                    autoComplete="current-password" 
                    required 
                    className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200"
                    />
                </div>

                <button 
                    type="submit" 
                    className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition duration-200"
                >
                    Entrar
                </button>
                </form>
            </div>
        </div>
    )
}