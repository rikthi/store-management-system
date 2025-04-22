import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";

export function EditInventory() {
    const { user } = useAuth();
    const { inventoryId } = useParams();
    const [inventoryData, setInventoryData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchInventory = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8081/${user.storeId}/inventory/${inventoryId}/get`
                );
                setInventoryData(response.data);
            } catch (err) {
                setError("Failed to fetch inventory data.");
            }
        };
        fetchInventory();
    }, [inventoryId, user.storeId]);

    useEffect(() => {
        const timer = setTimeout(() => {
            setSuccess("");
            setError("");
        }, 3000);
        return () => clearTimeout(timer);
    }, [success, error]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInventoryData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSuccess("");
        setError("");

        const { minimumStockLevel, maximumStockLevel } = inventoryData;
        const min = Number(minimumStockLevel);
        const max = Number(maximumStockLevel);

        if (min < 0 || max < 0) {
            setError("Stock levels cannot be negative.");
            return;
        }

        if (min > max) {
            setError("Minimum stock cannot be greater than maximum stock.");
            return;
        }

        setLoading(true);
        try {
            await axios.put(
                `http://localhost:8081/${user.storeId}/inventory/updateInventory`,
                {
                    ...inventoryData,
                    id: inventoryId,
                    minimumStockLevel: min,
                    maximumStockLevel: max,
                }
            );
            setSuccess("Inventory updated successfully!");
        } catch (err) {
            setError("Failed to update inventory.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
             style={{ backgroundImage: "url('../../../src/assets/loginBg.jpg')" }}>
            <div className="absolute inset-0 bg-black opacity-50" />
            <div className="relative z-10 bg-white bg-opacity-80 p-8 rounded-lg shadow-md w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">Edit Inventory</h2>

                {inventoryData ? (
                    <form onSubmit={handleSubmit} className="space-y-4">
                        {/* ID */}
                        <div>
                            <label className="block text-sm font-semibold mb-1">Inventory ID</label>
                            <input
                                type="text"
                                name="id"
                                value={inventoryData.id}
                                readOnly
                                className="w-full p-2 border rounded bg-gray-200"
                            />
                        </div>

                        {/* Category */}
                        <div>
                            <label className="block text-sm font-semibold mb-1">Category</label>
                            <input
                                type="text"
                                name="category"
                                value={inventoryData.category}
                                readOnly
                                className="w-full p-2 border rounded bg-gray-200"
                            />
                        </div>

                        {/* Quantity */}
                        <div>
                            <label className="block text-sm font-semibold mb-1">Quantity (Current Stock Level)</label>
                            <input
                                type="number"
                                name="currentStockLevel"
                                value={inventoryData.currentStockLevel}
                                readOnly
                                className="w-full p-2 border rounded bg-gray-200"
                            />
                        </div>

                        {/* Min */}
                        <div>
                            <label className="block text-sm font-semibold mb-1">Minimum Stock Level</label>
                            <input
                                type="number"
                                name="minimumStockLevel"
                                value={inventoryData.minimumStockLevel}
                                onChange={handleChange}
                                min={0}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>

                        {/* Max */}
                        <div>
                            <label className="block text-sm font-semibold mb-1">Maximum Stock Level</label>
                            <input
                                type="number"
                                name="maximumStockLevel"
                                value={inventoryData.maximumStockLevel}
                                onChange={handleChange}
                                min={0}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>

                        {/* Button */}
                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
                        >
                            {loading ? "Saving..." : "Update Inventory"}
                        </button>
                    </form>
                ) : (
                    <p className="text-center text-white">Loading inventory data...</p>
                )}

                {success && <p className="text-green-600 mt-4 text-center">{success}</p>}
                {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
            </div>
        </div>
    );
}
