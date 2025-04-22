import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";

export function AddInventory() {
    const { user } = useAuth();
    const [inventoryData, setInventoryData] = useState({
        id: "",
        category: "",
        currentStockLevel: "",
        minimumStockLevel: "",
        maximumStockLevel: "",
        storeId: user.storeId,
    });

    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInventoryData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    useEffect(() => {
        const timer = setTimeout(() => {
            setSuccess("");
            setError("");
        }, 3000);
        return () => clearTimeout(timer);
    }, [success, error]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSuccess("");
        setError("");

        const { category, currentStockLevel, minimumStockLevel, maximumStockLevel } = inventoryData;
        const current = Number(currentStockLevel);
        const min = Number(minimumStockLevel);
        const max = Number(maximumStockLevel);

        if (current < 0 || min < 0 || max < 0) {
            setError("Stock levels cannot be negative.");
            return;
        }

        if (min > max) {
            setError("Minimum stock cannot be greater than maximum stock.");
            return;
        }

        if (current > max) {
            setError("Current stock cannot be greater than maximum stock.");
            return;
        }

        setLoading(true);
        try {
            await axios.post(`http://localhost:8081/${user.storeId}/inventory/create`, {
                id: "",
                category,
                currentStockLevel: current,
                minimumStockLevel: min,
                maximumStockLevel: max,
                storeId: user.storeId
            });

            setSuccess("Inventory category added successfully!");
            setInventoryData({
                id: "",
                category: "",
                currentStockLevel: "",
                minimumStockLevel: "",
                maximumStockLevel: "",
                storeId: user.storeId,
            });
        } catch (err) {
            setError("Failed to add inventory category.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
             style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}>
            <div className="absolute inset-0 bg-black opacity-50" />
            <div className="relative z-10 bg-white bg-opacity-80 p-8 rounded-lg shadow-md w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">Add Inventory</h2>

                <form onSubmit={handleSubmit} className="space-y-4">
                    {[
                        { label: "Category Name", name: "category", type: "text" },
                        { label: "Minimum Stock Level", name: "minimumStockLevel", type: "number" },
                        { label: "Maximum Stock Level", name: "maximumStockLevel", type: "number" }
                    ].map(({ label, name, type }) => (
                        <div key={name}>
                            <label className="block text-sm font-semibold mb-1">{label}</label>
                            <input
                                type={type}
                                name={name}
                                value={inventoryData[name]}
                                min={type === "number" ? 0 : undefined}
                                onChange={handleChange}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>
                    ))}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
                    >
                        {loading ? "Submitting..." : "Add Inventory"}
                    </button>
                </form>

                {success && <p className="text-green-600 mt-4 text-center">{success}</p>}
                {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
            </div>
        </div>
    );
}
