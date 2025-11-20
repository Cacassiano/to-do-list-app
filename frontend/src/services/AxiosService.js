import axios from 'axios';

export default axios.create({
    // Adress for test
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-type": "application/json"
    },
    timeout: 5000
})