import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";

export function AddNewItem() {
    const { user } = useAuth();
    const { inventoryId, category } = useParams();
    const navigate = useNavigate();

    const [form, setForm] = useState({
        name: "",
        quantity: "",
        price: "",
        discountPercentage: "",
        manufactureDate: "",
        expirationDate: ""
    });

    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        setLoading(true);

        const { name, quantity, price, discountPercentage } = form;

        if (quantity < 0 || price < 0 || discountPercentage < 0) {
            setError("Quantity, price, and discount must be non-negative.");
            setLoading(false);
            return;
        }

        const payload = {
            id: "",
            name: form.name,
            manufactureDate: form.manufactureDate,
            price: parseFloat(form.price),
            expirationDate: form.expirationDate,
            discountPercentage: parseFloat(form.discountPercentage),
            inventoryId: parseInt(inventoryId),
            quantity: parseInt(form.quantity)
        };

        try {
            await axios.post(`http://localhost:8081/${user.storeId}/inventory/${inventoryId}/addItem`, payload);
            setSuccess("Item added successfully!");
            setTimeout(() => navigate("/ManagerOptions/ViewCategories"), 1500);
        } catch (err) {
            setError("Failed to add item.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div
            className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../../src/assets/loginBg.jpg')" }}>
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">Add New Item</h2>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="block text-sm font-semibold mb-1">Category</label>
                        <input
                            type="text"
                            value={category}
                            readOnly
                            className="w-full p-2 border rounded bg-gray-200"
                        />
                    </div>

                    {[
                        { label: "Item Name", name: "name", type: "text" },
                        { label: "Quantity", name: "quantity", type: "number" },
                        { label: "Price", name: "price", type: "number",  step: "0.01" },
                        { label: "Discount (%)", name: "discountPercentage", type: "number", step: "0.01" },
                        { label: "Manufacture Date", name: "manufactureDate", type: "date" },
                        { label: "Expiration Date", name: "expirationDate", type: "date" },
                    ].map(({ label, name, type }) => (
                        <div key={name}>
                            <label className="block text-sm font-semibold mb-1">{label}</label>
                            <input
                                type={type}
                                name={name}
                                min={["quantity", "price", "discountPercentage"].includes(name) ? 0 : undefined}
                                value={form[name]}
                                step="0.01"
                                onChange={handleChange}
                                required
                                className="w-full p-2 border rounded"
                            />
                        </div>
                    ))}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
                    >
                        {loading ? "Submitting..." : "Add Item"}
                    </button>
                </form>

                {success && <p className="text-green-600 mt-4 text-center">{success}</p>}
                {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
            </div>
        </div>
    );
}
