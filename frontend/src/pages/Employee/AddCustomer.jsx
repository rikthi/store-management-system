import React, { useState } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx"; // Make sure the path is correct

export function AddCustomer() {
    const { user } = useAuth(); // Access the logged-in employee with storeId

    const [customer, setCustomer] = useState({
        id: "",
        name: "",
        email: "",
        membershipType: "BRONZE",
        storeId: user.storeId,
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    // Handle input changes
    const handleChange = (e) => {
        setCustomer({ ...customer, [e.target.name]: e.target.value });
    };

    // Submit customer data to backend
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        try {
            await axios.post(`http://localhost:8081/${user.storeId}/customer/create`, customer);
            setCustomer({
                id: "",
                name: "",
                email: "",
                membershipType: "",
                storeId: user.storeId,
            });
            setSuccess("Customer added successfully!");
        } catch (err) {
            setError("Error adding customer. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div
            className="relative flex flex-col items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            {/* Overlay for better readability */}
            <div className="absolute inset-0 bg-black opacity-50"></div>

            {/* Content container */}
            <div className="relative z-10 flex flex-col items-center justify-center space-y-6 bg-white bg-opacity-90 p-8 rounded-lg shadow-lg">
                <h1 className="text-3xl font-bold text-gray-800">Add Customer</h1>

                {/* Form */}
                <form onSubmit={handleSubmit} className="space-y-4 w-80">
                    <input
                        type="text"
                        name="name"
                        value={customer.name}
                        onChange={handleChange}
                        placeholder="Customer Name"
                        className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />
                    <input
                        type="email"
                        name="email"
                        value={customer.email}
                        onChange={handleChange}
                        placeholder="Customer Email"
                        className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />
                    <select
                        name="membershipType"
                        value={customer.membershipType}
                        onChange={handleChange}
                        className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="BRONZE">Bronze</option>
                        <option value="SILVER">Silver</option>
                        <option value="GOLD">Gold</option>
                    </select>

                    <button
                        type="submit"
                        className="w-full bg-green-500 text-white p-3 rounded-lg hover:bg-green-600 transition"
                        disabled={loading}
                    >
                        {loading ? "Adding..." : "Add Customer"}
                    </button>
                </form>

                {/* Feedback messages */}
                {error && <p className="text-red-500 mt-4">{error}</p>}
                {success && <p className="text-green-500 mt-4">{success}</p>}
            </div>
        </div>
    );
}
