import { test } from "../services/AuthService.js";

export default function RegisterPage() {
    return (
        <div>
            <h1>Register form</h1>
            <form onSubmit={test}>
                <input type="submit" />
            </form>
        </div>
    );
}