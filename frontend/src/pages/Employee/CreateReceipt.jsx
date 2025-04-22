import React, { useState } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx"; // adjust if the path is different

export function CreateReceipt() {
    const { user } = useAuth(); // Get employee info (assuming employeeId is stored in user.username)

    const [receipt, setReceipt] = useState({
        id: "",
        dateOfTransaction: "",
        cardNo: "",
        totalAmount: "",
        customerId: "",
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleChange = (e) => {
        setReceipt({ ...receipt, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        if (parseFloat(receipt.totalAmount) < 0) {
            setError("Total price cannot be negative.");
            setLoading(false);
            return;
        }

        try {
            await axios.post(`http://localhost:8081/${user.storeId}/customer/receipt/create`, receipt);
                setSuccess("Receipt added successfully!");
                setReceipt({
                    id: "",
                    dateOfTransaction: "",
                    cardNo: "",
                    totalAmount: "",
                    customerId: "",
                });
        } catch (err) {
            setError("Error adding receipt. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div
            className="relative flex flex-col items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            <div className="absolute inset-0 bg-black opacity-50"></div>

            <div className="relative z-10 flex flex-col items-center bg-white bg-opacity-80 p-8 rounded-lg shadow-lg w-96">
                <h1 className="text-2xl font-bold text-gray-800 mb-4">Create Receipt</h1>

                <form onSubmit={handleSubmit} className="w-full space-y-4">
                    <input
                        type="text"
                        name="customerId"
                        placeholder="Customer ID"
                        value={receipt.customerId}
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />

                    <input
                        type="number"
                        name="totalAmount"
                        placeholder="Total Price"
                        value={receipt.totalAmount}
                        step= {0.01}
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        min="0"
                        required
                    />

                    <input
                        type="date"
                        name="dateOfTransaction"
                        value={receipt.dateOfTransaction}
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />

                    <input
                        type="text"
                        name="cardNo"
                        placeholder="Card Number"
                        maxLength={16}
                        value={receipt.cardNo}
                        pattern="\d{16}"
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required
                    />

                    <button
                        type="submit"
                        className="w-full bg-green-500 text-white p-3 rounded-lg hover:bg-green-600 transition"
                        disabled={loading}
                    >
                        {loading ? "Processing..." : "Add Receipt"}
                    </button>
                </form>

                {success && <p className="text-green-500 mt-4">{success}</p>}
                {error && <p className="text-red-500 mt-4">{error}</p>}
            </div>
        </div>
    );
}
