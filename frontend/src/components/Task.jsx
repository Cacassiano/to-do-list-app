import { useState } from "react";

export default function Task({id, title, description, created_at, status, updated_at, onDelete, onStatusChange}) {    

    return(
        <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-blue-500 hover:shadow-lg transition-shadow duration-200 w-full max-w-sm sm:max-w-md md:max-w-lg mx-auto" id={id}>
            <div className="flex items-start justify-between mb-3">
                <h3 className="text-lg font-semibold text-gray-900 line-clamp-1">{title}</h3>
            </div>
            
            <p className="text-gray-600 mb-4 line-clamp-2">{description}</p>
            
            <div className="flex gap-2 mb-4">
                {/* Pendente */}
                <label className="flex items-center cursor-pointer">
                <input 
                    type="radio" 
                    name={`status-${id}`} 
                    value="PENDING"
                    checked={status === 'PENDING'}
                    onChange={() => onStatusChange("PENDING")}
                    className="hidden"
                />
                <span className={`px-3 py-1 rounded-full text-xs font-medium transition-all duration-200 ${
                    status === 'PENDING' 
                    ? 'bg-yellow-100 text-yellow-800 border-2 border-yellow-400' 
                    : 'bg-gray-100 text-gray-600 border-2 border-gray-300 hover:bg-gray-200'
                }`}>
                    Pendente
                </span>
                </label>

                <label className="flex items-center cursor-pointer">
                <input 
                    type="radio" 
                    name={`status-${id}`} 
                    value="IN_PROGRESS"
                    checked={status === 'IN_PROGRESS'}
                    onChange={() => onStatusChange("IN_PROGRESS")}
                    className="hidden"
                />
                <span className={`px-3 py-1 rounded-full text-xs font-medium transition-all duration-200 ${
                    status === 'IN_PROGRESS' 
                    ? 'bg-blue-100 text-blue-800 border-2 border-blue-400' 
                    : 'bg-gray-100 text-gray-600 border-2 border-gray-300 hover:bg-gray-200'
                }`}>
                    Em andamento
                </span>
                </label>

                {/* Completa */}
                <label className="flex items-center cursor-pointer">
                <input 
                    type="radio" 
                    name={`status-${id}`} 
                    value="COMPLETED"
                    checked={status === 'COMPLETED'}
                    onChange={() => onStatusChange("COMPLETED")}
                    className="hidden"
                />
                <span className={`px-3 py-1 rounded-full text-xs font-medium transition-all duration-200 ${
                    status === 'COMPLETED' 
                    ? 'bg-green-100 text-green-800 border-2 border-green-400' 
                    : 'bg-gray-100 text-gray-600 border-2 border-gray-300 hover:bg-gray-200'
                }`}>
                    Completa
                </span>
                </label>
            </div>
            
            <div className="flex justify-between items-center mt-4">
                <div className="flex flex-col sm:flex-row sm:gap-4 text-sm text-gray-500">
                    <span className="flex items-center gap-1">
                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        Criado em: {new Date(created_at).toLocaleDateString()}
                    </span>
                    <span className="flex items-center gap-1">
                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                        </svg>
                        Atualizado em: {new Date(updated_at).toLocaleDateString()}
                    </span>
                </div>
                <button
                    onClick={onDelete}
                    className="text-red-500 hover:text-red-700 transition duration-200"
                    title="Excluir tarefa"
                >
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                </button>
            </div>
        </div>
    );
}