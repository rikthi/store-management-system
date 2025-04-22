import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export function CreateStore() {
    const [storeData, setStoreData] = useState({
        id: "",
        name: "",
        address: "",
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setStoreData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        try {
            const response = await axios.post(`http://localhost:8081/stores/create`, storeData);
            const createdStore = response.data; // expecting { id, name, address }
            setSuccess("Store created successfully!");

            // Navigate to CreateManager page and pass storeId
            navigate(`/CreateManager/${createdStore.id}`);
        } catch (err) {
            if (err.response?.data) {
                setError(err.response.data);
            } else {
                setError("Failed to create store.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div
            className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            <div className="absolute inset-0 bg-black opacity-50"></div>
            <div className="relative z-10 bg-white bg-opacity-80 p-8 rounded-lg shadow-lg w-full max-w-md">
                <h1 className="text-2xl font-bold mb-6 text-center text-gray-800">Create Store</h1>

                <form onSubmit={handleSubmit} className="space-y-4">
                    {[{ label: "Store Name", name: "name" }, { label: "Store Address", name: "address" }].map(({ label, name }) => (
                        <div key={name}>
                            <label className="block text-sm font-semibold text-gray-700">{label}</label>
                            <input
                                type="text"
                                name={name}
                                value={storeData[name]}
                                onChange={handleChange}
                                className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                required
                            />
                        </div>
                    ))}

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
                    >
                        {loading ? "Creating..." : "Create Store"}
                    </button>
                </form>

                {error && <p className="text-red-500 mt-4">{error}</p>}
                {success && <p className="text-green-500 mt-4">{success}</p>}
            </div>
        </div>
    );
}
