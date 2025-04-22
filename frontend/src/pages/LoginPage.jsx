import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {useAuth} from "./AuthContext.jsx";

export function LoginPage() {
    const { setUser } = useAuth(); // Get setUser function from context
    const [userId, setUserId] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const response = await axios.post("http://localhost:8081/user/login", {
                userId, email, password,
            });

            const { role, storeId } = response.data;
            setUser({ userId, role, storeId});
            // Navigate based on role
            if (role === "MANAGER") navigate("/ManagerHome");
            else if (role === "SUPERVISOR") navigate("/SupervisorHome");
            else if (role === "HOURLY_EMPLOYEE") navigate("/HourlyEmployee");
            else if (role === "SALARIED_EMPLOYEE") navigate("/SalariedEmployee");
            else if (role === "CUSTOMER") navigate("/CustomerHome");
            else setError("Wrong Credentials");

        } catch (err) {
            setError(err.message("Invalid username or password"));
        }
    };

    return (
        <div className="page-background"
             style={{ backgroundImage: "url('src/assets/loginBg.jpg')" }}
        >
            <div className="bg-white p-8 rounded-lg shadow-lg w-96 text-center bg-opacity-90">
                <h2 className="text-2xl font-bold mb-6">Login</h2>
                <form onSubmit={handleLogin} className="flex flex-col gap-4">
                    <input
                        type="text"
                        placeholder="UserID"
                        required={true}
                        className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        value={userId}
                        onChange={(e) => setUserId(e.target.value)}
                    />
                    <input
                        type="emailAddress"
                        placeholder="Email"
                        required={true}
                        className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        required={true}
                        className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {error && <p className="text-red-500 mt-2">{error}</p>}
                    <button type="submit" className="w-full bg-green-500 text-white p-3 rounded-lg hover:bg-green-600 transition">
                        Log In
                    </button>
                    <button
                        type="button"
                        className="w-full bg-gray-300 text-gray-800 p-3 rounded-lg hover:bg-gray-400 transition"
                        onClick={() => navigate("/register")}
                    >
                        Register
                    </button>
                </form>
            </div>
        </div>
    );
}


