import React, { useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";

export function CreateManager() {
    const { storeId } = useParams();
    const [managerData, setManagerData] = useState({
        employee: {
            id: "",
            name: "",
            gender: "Male",
            phoneNumber: "",
            dateOfBirth: "",
            emailAddress: "",
            address: "",
            supervisorId: "",
            storeId: ""
        }
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setManagerData((prev) => ({
            ...prev,
            employee: {
                ...prev.employee,
                [name]: value
            }
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        const payload = {
            ...managerData,
            employee: {
                ...managerData.employee,
                storeId: parseInt(storeId),
            }
        };

        try {
            const response = await axios.post(
                `http://localhost:8081/${storeId}/employees/create/manager`,
                payload
            );

            const createdId = response.data.employee.id;
            setSuccess(`✅ Manager created successfully with ID: ${createdId}. Make sure to remember this ID!`);
        } catch (err) {
            if (err.response?.data) {
                setError(err.response.data);
            } else {
                setError("❌ Failed to create manager.");
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
                <h1 className="text-2xl font-bold mb-6 text-center text-gray-800">Create Manager</h1>

                <form onSubmit={handleSubmit} className="space-y-4">
                    {[
                        { label: "Name", name: "name", type: "text" },
                        { label: "Phone Number", name: "phoneNumber", type: "tel" },
                        { label: "Date of Birth", name: "dateOfBirth", type: "date" },
                        { label: "Email Address", name: "emailAddress", type: "email" },
                        { label: "Address", name: "address", type: "text" },
                    ].map(({ label, name, type }) => (
                        <div key={name}>
                            <label className="block text-sm font-semibold text-gray-700">{label}</label>
                            <input
                                type={type}
                                name={name}
                                value={managerData.employee[name]}
                                onChange={handleChange}
                                className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                required
                            />
                        </div>
                    ))}

                    <div>
                        <label className="block text-sm font-semibold text-gray-700">Gender</label>
                        <select
                            name="gender"
                            value={managerData.employee.gender}
                            onChange={handleChange}
                            className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        >
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-500 text-white p-3 rounded hover:bg-blue-600 transition"
                    >
                        {loading ? "Creating..." : "Create Manager"}
                    </button>
                </form>

                {error && <p className="text-red-500 mt-4">{error}</p>}
                {success && (
                    <>
                        <p className="text-green-600 mt-4 font-semibold">{success}</p>
                        <Link
                            to="/register"
                            className="block mt-4 w-full text-center bg-purple-600 text-white py-2 rounded hover:bg-purple-700 transition"
                        >
                            Go to Registration Page
                        </Link>
                    </>
                )}
            </div>
        </div>
    );
}
