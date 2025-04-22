import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";

export function OrderItem() {
    const { user } = useAuth();
    const { itemId } = useParams();
    const navigate = useNavigate();

    const [item, setItem] = useState(null);
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchItem = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/${user.storeId}/inventory/items/${itemId}/get`);
                setItem(response.data);
            } catch (err) {
                setError("Failed to fetch item.");
            }
        };
        fetchItem();
    }, [itemId, user.storeId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setItem((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        setLoading(true);

        if (item.price < 0 || item.discountPercentage < 0) {
            setError("Price and discount must be non-negative.");
            setLoading(false);
            return;
        }

        const payload = {
            ...item,
            price: parseFloat(item.price),
            discountPercentage: parseFloat(item.discountPercentage),
            quantity: parseInt(item.quantity)
        };

        try {
            await axios.put(`http://localhost:8081/${user.storeId}/inventory/updateItem`, payload);
            setSuccess("Item updated successfully!");
            setTimeout(() => navigate("/ManagerOptions/ViewCategories"), 2000);
        } catch (err) {
            setError("Failed to update item.");
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async () => {
        const confirmed = window.confirm("Are you sure you want to delete this item?");
        if (!confirmed) return;

        try {
            await axios.delete(`http://localhost:8081/${user.storeId}/inventory/items/${itemId}/delete`);
            alert("Item deleted successfully.");
            navigate("/ManagerOptions/ViewCategories");
        } catch (err) {
            alert("Failed to delete item.");
        }
    };

    return (
        <div
            className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">Order Item</h2>

                {item ? (
                    <form onSubmit={handleSubmit} className="space-y-4">
                        {[
                            { label: "Item ID", name: "id" },
                            { label: "Name", name: "name" },
                            { label: "Manufacture Date", name: "manufactureDate", type: "date" },
                            { label: "Expiration Date", name: "expirationDate", type: "date" }
                        ].map(({ label, name, type }) => (
                            <div key={name}>
                                <label className="block text-sm font-semibold mb-1">{label}</label>
                                <input
                                    type={type || "text"}
                                    value={item[name]}
                                    readOnly
                                    className="w-full p-2 border rounded bg-gray-200"
                                />
                            </div>
                        ))}

                        <div>
                            <label className="block text-sm font-semibold mb-1">Price</label>
                            <input
                                type="number"
                                name="price"
                                value={item.price}
                                onChange={handleChange}
                                step="0.01"
                                min={0}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-semibold mb-1">Discount (%)</label>
                            <input
                                type="number"
                                name="discountPercentage"
                                value={item.discountPercentage}
                                onChange={handleChange}
                                step="0.01"
                                min={0}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-semibold mb-1">New Total Quantity</label>
                            <input
                                type="number"
                                name="quantity"
                                value={item.quantity}
                                onChange={handleChange}
                                min={0}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
                        >
                            {loading ? "Updating..." : "Update Item"}
                        </button>

                        <button
                            type="button"
                            onClick={handleDelete}
                            className="w-full bg-red-500 text-white p-3 rounded hover:bg-red-600 transition"
                        >
                            Delete Item
                        </button>
                    </form>
                ) : (
                    <p className="text-center text-gray-700">Loading item data...</p>
                )}

                {success && <p className="text-green-600 mt-4 text-center">{success}</p>}
                {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
            </div>
        </div>
    );
}
