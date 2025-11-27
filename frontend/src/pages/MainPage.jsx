import { useState, useEffect } from "react";
import Task from "../components/Task.jsx";
import { useNavigate } from "react-router-dom";
import api from "../services/AxiosService.js";

export default function MainPage() {
    const [token] = useState(localStorage.getItem("token"));
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [newTaskTitle, setNewTaskTitle] = useState("");
    const [newTaskDescription, setNewTaskDescription] = useState("");
    const [showAddForm, setShowAddForm] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        validate_token();
    }, [token, navigate]);

    useEffect(() => {
        if (token) {
            fetchTasks();
        }
    }, [token]);

    async function validate_token() {
        try {
            if (!token) throw new Error("No token");
            const res = await api.get("/auth/check", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (res.status !== 200) throw new Error("Invalid token");
        } catch (e) {
            console.error(e);
            localStorage.removeItem("token");
            navigate("/login");
        }
    }

    async function fetchTasks() {
        try {
            setLoading(true);
            const res = await api.get("/tasks", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setTasks(res.data.data);
        } catch (e) {
            console.error("Erro ao buscar tasks:", e);
            setError("Erro ao carregar tarefas");
        } finally {
            setLoading(false);
        }
    }

    async function handleAddTask(e) {
        e.preventDefault();
        if (!newTaskTitle.trim()) return;

        try {
            const res = await api.post("/tasks", 
                {
                    title: newTaskTitle,
                    description: newTaskDescription,
                    status: "PENDING"
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            
            setTasks([...tasks, res.data]);
            setNewTaskTitle("");
            setNewTaskDescription("");
            setShowAddForm(false);
        } catch (e) {
            console.error("Erro ao criar task:", e);
            setError("Erro ao criar tarefa");
        }
    }

    async function handleStatusChange(task, newStatus) {
        task.status = newStatus
        try {
            await api.put(`/tasks/${task.id}`, 
                {...task},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            
            // Atualiza o estado local
            setTasks(tasks.map(t => 
                t.id === task.id ? task : t
            ));
        } catch (e) {
            console.error("Erro ao atualizar status:", e);
            setError("Erro ao atualizar tarefa");
        }
    }

    async function handleDeleteTask(taskId) {
        try {
            await api.delete(`/tasks/${taskId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            
            setTasks(tasks.filter(task => task.id !== taskId));
        } catch (e) {
            console.error("Erro ao deletar task:", e);
            setError("Erro ao deletar tarefa");
        }
    }

    function handleLogout() {
        localStorage.removeItem("token");
        navigate("/login");
    }

    // Filtros para as tasks
    const pendingTasks = tasks.filter(task => task.status === "PENDING");
    const inProgressTasks = tasks.filter(task => task.status === "IN_PROGRESS");
    const completedTasks = tasks.filter(task => task.status === "COMPLETED");

    if (loading) {
        return (
            <div className="min-h-screen bg-gray-50 flex items-center justify-center">
                <div className="text-center">
                    <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
                    <p className="mt-4 text-gray-600">Carregando tarefas...</p>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-50 py-8">
            {/* Header */}
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center mb-8">
                    <div>
                        <h1 className="text-3xl font-bold text-gray-900">Minhas Tarefas</h1>
                        <p className="text-gray-600 mt-2">Organize seu dia a dia</p>
                    </div>
                    <button
                        onClick={handleLogout}
                        className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg transition duration-200"
                    >
                        Sair
                    </button>
                </div>

                {/* Botão Adicionar Task */}
                <div className="mb-6">
                    <button
                        onClick={() => setShowAddForm(!showAddForm)}
                        className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg font-medium flex items-center gap-2 transition duration-200"
                    >
                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4" />
                        </svg>
                        Nova Tarefa
                    </button>
                </div>

                {/* Formulário de Adicionar Task */}
                {showAddForm && (
                    <div className="bg-white rounded-lg shadow-md p-6 mb-6">
                        <h2 className="text-xl font-semibold mb-4">Nova Tarefa</h2>
                        <form onSubmit={handleAddTask} className="space-y-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    Título *
                                </label>
                                <input
                                    type="text"
                                    value={newTaskTitle}
                                    onChange={(e) => setNewTaskTitle(e.target.value)}
                                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    placeholder="Digite o título da tarefa"
                                    required
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    Descrição
                                </label>
                                <textarea
                                    value={newTaskDescription}
                                    onChange={(e) => setNewTaskDescription(e.target.value)}
                                    className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    placeholder="Digite a descrição da tarefa"
                                    rows="3"
                                />
                            </div>
                            <div className="flex gap-3">
                                <button
                                    type="submit"
                                    className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md transition duration-200"
                                >
                                    Adicionar
                                </button>
                                <button
                                    type="button"
                                    onClick={() => setShowAddForm(false)}
                                    className="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded-md transition duration-200"
                                >
                                    Cancelar
                                </button>
                            </div>
                        </form>
                    </div>
                )}

                {/* Mensagem de Erro */}
                {error && (
                    <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
                        {error}
                    </div>
                )}

                {/* Grid de Tasks */}
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                    {/* Coluna Pendente */}
                    <div className="space-y-4">
                        <h2 className="text-lg font-semibold text-gray-700 bg-yellow-100 px-4 py-2 rounded-lg">
                            Pendentes ({pendingTasks.length})
                        </h2>
                        {pendingTasks.map(task => (
                            <Task
                                key={task.id}
                                {...task}
                                onStatusChange={(newStatus) => handleStatusChange(task, newStatus)}
                                onDelete={() => handleDeleteTask(task.id)}
                            />
                        ))}
                        {pendingTasks.length === 0 && (
                            <p className="text-gray-500 text-center py-4">Nenhuma tarefa pendente</p>
                        )}
                    </div>

                    {/* Coluna Em Progresso */}
                    <div className="space-y-4">
                        <h2 className="text-lg font-semibold text-gray-700 bg-blue-100 px-4 py-2 rounded-lg">
                            Em Andamento ({inProgressTasks.length})
                        </h2>
                        {inProgressTasks.map(task => (
                            <Task
                                key={task.id}
                                {...task}
                                onStatusChange={(newStatus) => handleStatusChange(task, newStatus)}
                                onDelete={() => handleDeleteTask(task.id)}
                            />
                        ))}
                        {inProgressTasks.length === 0 && (
                            <p className="text-gray-500 text-center py-4">Nenhuma tarefa em andamento</p>
                        )}
                    </div>

                    {/* Coluna Completa */}
                    <div className="space-y-4">
                        <h2 className="text-lg font-semibold text-gray-700 bg-green-100 px-4 py-2 rounded-lg">
                            Concluídas ({completedTasks.length})
                        </h2>
                        {completedTasks.map(task => (
                            <Task
                                key={task.id}
                                {...task}
                                onStatusChange={(newStatus) => handleStatusChange(task, newStatus)}
                                onDelete={() => handleDeleteTask(task.id)}
                            />
                        ))}
                        {completedTasks.length === 0 && (
                            <p className="text-gray-500 text-center py-4">Nenhuma tarefa concluída</p>
                        )}
                    </div>
                </div>

                {/* Total de Tasks */}
                <div className="mt-8 text-center text-gray-600">
                    Total de tarefas: {tasks.length}
                </div>
            </div>
        </div>
    );
}