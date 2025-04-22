import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export function RegistrationPage() {
    const [employeeId, setEmployeeId] = useState("");
    const [userId] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSignIn = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const response = await axios.post("http://localhost:8081/user/create", {
                employeeId,
                userId,
                password,
                email
            });


            if (response.data != null) {
                navigate("/");
            } else {
                setError("Login failed. Please check your credentials.");
            }
        } catch (err) {
            if (err.response && err.response.data && typeof err.response.data === "string") {
                // This handles the error message sent from the backend
                setError(err.response.data);
            } else {
                setError("An unexpected error occurred.");
            }
        }
    };


    return (
        <div
            className="flex items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('src/assets/loginBg.jpg')" }}
        >
            <div className="bg-white p-8 rounded-lg shadow-lg w-96">
                <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">Sign Up</h2>
                <form onSubmit={handleSignIn} className="flex flex-col gap-4">
                    <input
                        type="text"
                        placeholder="UserId"
                        required={true}
                        className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-500"
                        onChange={(e) => setEmployeeId(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        required={true}
                        className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-500"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        required={true}
                        className="w-full p-3 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-green-500"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    {error && <p className="text-red-500">{error}</p>}
                    <button className="w-full bg-green-600 text-white p-3 rounded-lg hover:bg-green-700 transition">
                        Register
                    </button>
                </form>

                <p className="mt-4 text-center text-gray-600">
                    Already have an account?{" "}
                    <button
                        onClick={() => navigate("/")}
                        className="text-green-600 hover:underline"
                    >
                        Login
                    </button>
                </p>

                <p className="mt-2 text-center text-gray-600">
                    Want to create a store?{" "}
                    <button
                        onClick={() => navigate("/CreateStore")}
                        className="text-blue-600 hover:underline"
                    >
                        Create a Store
                    </button>
                </p>
            </div>
        </div>
    );
}
