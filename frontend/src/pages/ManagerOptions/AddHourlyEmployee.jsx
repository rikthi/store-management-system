import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx"; // Ensure path is correct

export function AddHourlyEmployee() {
    const { user } = useAuth(); // manager with storeId

    const [employeeData, setEmployeeData] = useState({
        id: "",
        name: "",
        gender: "Male",
        phoneNumber: "",
        dateOfBirth: "",
        emailAddress: "",
        address: "",
        supervisorId: "",
    });

    const [payScale, setPayScale] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEmployeeData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    // Clear messages after 3 seconds
    useEffect(() => {
        const timer = setTimeout(() => {
            setSuccess("");
            setError("");
        }, 3000);
        return () => clearTimeout(timer);
    }, [success, error]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        if (parseFloat(payScale) < 0) {
            setError("Pay scale cannot be negative.");
            setLoading(false);
            return;
        }

        try {
            await axios.post(`http://localhost:8081/${user.storeId}/employees/create/hourlyEmployee`, {
                employee: employeeData,
                payScale: parseFloat(payScale)
            });

            setSuccess("Employee added successfully!");
            setEmployeeData({
                id: "",
                name: "",
                gender: "",
                phoneNumber: "",
                dateOfBirth: "",
                emailAddress: "",
                address: "",
                supervisorId: "",
            });
            setPayScale("");
        } catch (err) {
            setError("Error adding employee. Please try again.");
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

            <div className="relative z-10 flex flex-col items-center justify-center space-y-6 bg-white bg-opacity-80 p-8 rounded-lg shadow-lg">
                <h1 className="text-3xl font-bold text-gray-800">Add Hourly Employee</h1>

                <form onSubmit={handleSubmit} className="space-y-4 w-80">
                    {["name", "dateOfBirth", "phoneNumber", "emailAddress", "address", "supervisorId"].map((field) => (
                        <div key={field}>
                            <label htmlFor={field} className="block text-sm font-semibold text-gray-700">
                                {field === "dateOfBirth" ? "Date of Birth" : field.charAt(0).toUpperCase() + field.slice(1).replace(/([A-Z])/g, ' $1')}
                            </label>
                            {field === "address" ? (
                                <textarea
                                    id={field}
                                    name={field}
                                    value={employeeData[field]}
                                    onChange={handleChange}
                                    className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    required
                                />
                            ) : (
                                <input
                                    type={field === "dateOfBirth" ? "date" : field === "phoneNumber" || field === "supervisorId" ? "tel" : "text"}
                                    id={field}
                                    name={field}
                                    value={employeeData[field]}
                                    onChange={handleChange}
                                    min={field === "supervisor" ? "1" : undefined}
                                    className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    required
                                />
                            )}
                        </div>
                    ))}

                    {/* Gender dropdown */}
                    <div>
                        <label htmlFor="gender" className="block text-sm font-semibold text-gray-700">Gender</label>
                        <select
                            id="gender"
                            name="gender"
                            value={employeeData.gender}
                            onChange={handleChange}
                            className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        >
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>

                    {/* Pay Scale */}
                    <div>
                        <label htmlFor="payScale" className="block text-sm font-semibold text-gray-700">Pay Scale</label>
                        <input
                            type="number"
                            id="payScale"
                            name="payScale"
                            value={payScale}
                            onChange={(e) => setPayScale(e.target.value)}
                            min="0"
                            className="w-full p-3 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                            required
                        />
                    </div>

                    {/* Submit Button */}
                    <button
                        type="submit"
                        className="w-full bg-blue-500 text-white p-3 rounded-lg hover:bg-blue-600 transition"
                        disabled={loading}
                    >
                        {loading ? "Submitting..." : "Add Employee"}
                    </button>
                </form>

                {/* Messages */}
                {error && <p className="mt-4 text-red-500">{error}</p>}
                {success && <p className="mt-4 text-green-500">{success}</p>}
            </div>
        </div>
    );
}