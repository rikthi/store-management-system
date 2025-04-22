import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";

export function EditManagerInformation() {
    const { user } = useAuth();
    const [manager, setManager] = useState(null);
    const [form, setForm] = useState({});
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchManager = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/${user.storeId}/employees/get/manager`);
                setManager(response.data);
                setForm({ ...response.data.employee });
            } catch (err) {
                setError("Failed to fetch manager details.");
            }
        };
        fetchManager();
    }, [user.storeId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setSuccess("");
        setError("");

        try {
            await axios.put(`http://localhost:8081/${user.storeId}/employees/update/manager`, { employee: form });
            setSuccess("Manager information updated successfully.");
        } catch (err) {
            setError("Failed to update manager information.");
        } finally {
            setLoading(false);
        }
    };

    if (!manager) return <p className="text-center mt-10 text-white">Loading manager information...</p>;

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
             style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}>
            <div className="bg-white bg-opacity-90 p-6 rounded-lg shadow-lg w-full max-w-lg">
                <h1 className="text-2xl font-bold text-center mb-6">Edit Manager Information</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                    <label className="block font-semibold">Id</label>
                       <input
                           name="id"
                        value={form.id}
                           disabled className="w-full p-2 border rounded bg-gray-300"
                        />
                    </div>
                    {["name", "phoneNumber", "dateOfBirth", "emailAddress", "address"].map(field => (
                        <div key={field}>
                            <label className="block font-semibold capitalize">{field.replace(/([A-Z])/g, ' $1')}</label>
                            <input
                                type={field === "dateOfBirth" ? "date" : "text"}
                                name={field}
                                value={form[field] || ""}
                                onChange={handleChange}
                                className="w-full p-2 border rounded"
                                required
                            />
                        </div>
                    ))}

                    <div>
                        <label className="block font-semibold">Gender</label>
                        <select
                            name="gender"
                            value={form.gender || ""}
                            onChange={handleChange}
                            className="w-full p-2 border rounded"
                        >
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600"
                        disabled={loading}
                    >
                        {loading ? "Updating..." : "Update Manager"}
                    </button>

                    {success && <p className="text-green-600 text-center mt-4">{success}</p>}
                    {error && <p className="text-red-600 text-center mt-4">{error}</p>}
                </form>
            </div>
        </div>
    );
}
