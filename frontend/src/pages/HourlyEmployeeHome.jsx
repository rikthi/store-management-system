import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth } from "./AuthContext.jsx";
import { useEffect } from "react";

export function HourlyEmployeeHome() {
    const navigate = useNavigate();
    const { user } = useAuth();

    const [personalDetails] = useState(null);
    const [payScale, setPayScale] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    // Get timestamp in Calgary local time as ISO string without milliseconds
    const getCalgaryTimestamp = () => {
        const date = new Date().toLocaleString("en-CA", {
            timeZone: "America/Edmonton",
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
            hour12: false,
        });

        // Convert to ISO 8601 format expected by Java LocalDateTime
        const [datePart, timePart] = date.split(", ");
        return `${datePart}T${timePart}`;
    };
    useEffect(() => {
        if (error) {
            const timer = setTimeout(() => {
                setError("");
            }, 3000);
            return () => clearTimeout(timer);
        }
    }, [error]);
    const fetchPayScale = async () => {
        setLoading(true);
        setError("");
        try {
            const response = await axios.get(`http://localhost:8081/${user.storeId}/employees/get/hourlyEmployee/${user.userId}`, {
            });
            setPayScale(response.data.payScale);
        } catch (err) {
            setError("Failed to fetch pay scale");
        } finally {
            setLoading(false);
        }
    };

    const punchIn = async () => {
        const timestamp = getCalgaryTimestamp();
        const confirmed = window.confirm(`Confirm Punch In at ${timestamp}?`);
        if (!confirmed) return;

        try {
            const response = await axios.post(`http://localhost:8081/${user.storeId}/attendance/punchIn`, {
                id: "",
                employeeId: user.userId,
                verifierId: "",
                punchInTime: timestamp,
                punchOutTime: null,
                isVerified: false,
            });
            alert("Punch In recorded.");
        } catch (err) {
            alert("Failed to record Punch In.");
        }
    };

    const punchOut = async () => {
        const timestamp = getCalgaryTimestamp();
        const confirmed = window.confirm(`Confirm Punch Out at ${timestamp}?`);
        if (!confirmed) return;


        try {
            await axios.put(`http://localhost:8081/${user.storeId}/attendance/punchOut`, {
                id: user.userId,
                punchOutTime: timestamp,
            });
            alert("Punch Out recorded.");
        } catch (err) {
            alert("Failed to record Punch Out.");
        }
    };

    return (
        <div
            className="relative flex flex-col items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('src/assets/loginBg.jpg')" }}
        >
            <div className="absolute inset-0 bg-black opacity-50"></div>
            <div className="relative z-10 flex flex-col items-center justify-center space-y-6 bg-white bg-opacity-80 p-8 rounded-lg shadow-lg">
                <h1 className="text-3xl font-bold text-gray-800">Hourly Employee Dashboard</h1>

                <div className="space-y-4 w-80">
                    <button onClick={() => navigate("/Employee/ViewEmployeeInfo")} className="w-full bg-blue-500 text-white p-3 rounded-lg hover:bg-blue-600 transition">
                        View Personal Information
                    </button>
                    <button onClick={fetchPayScale} className="w-full bg-blue-500 text-white p-3 rounded-lg hover:bg-blue-600 transition">
                        View Pay Scale
                    </button>
                    <button
                        onClick={() => navigate("/StoreDetails")}
                        className="btn-primary"
                    >
                        View Store Details
                    </button>
                    <button onClick={punchIn} className="w-full bg-green-500 text-white p-3 rounded-lg hover:bg-green-600 transition">
                        Punch In
                    </button>
                    <button onClick={punchOut} className="w-full bg-red-500 text-white p-3 rounded-lg hover:bg-red-600 transition">
                        Punch Out
                    </button>
                    <button onClick={() => navigate("/Employee/AddCustomer")} className="w-full bg-yellow-500 text-white p-3 rounded-lg hover:bg-yellow-600 transition">
                        Add a Customer
                    </button>
                    <button onClick={() => navigate("/Employee/ViewCustomers")} className="w-full bg-yellow-500 text-white p-3 rounded-lg hover:bg-yellow-600 transition">
                        View Customers
                    </button>
                    <button onClick={() => navigate("/Employee/CreateReceipt")} className="w-full bg-purple-500 text-white p-3 rounded-lg hover:bg-purple-600 transition">
                        Create Receipt
                    </button>
                    <button
                        onClick={() => navigate("/")}
                        className="btn-primary"
                    >
                        Logout
                    </button>
                </div>

                {loading && <p className="text-blue-500 mt-4">Loading...</p>}
                {error && <p className="text-red-500 mt-4">{error}</p>}

                {personalDetails && (
                    <div className="mt-6 w-full max-w-md bg-white p-4 rounded-lg shadow-md">
                        <h2 className="text-xl font-semibold mb-4">Personal Details</h2>
                        <p><strong>Name:</strong> {personalDetails.name}</p>
                        <p><strong>Email:</strong> {personalDetails.email}</p>
                        <p><strong>Phone:</strong> {personalDetails.phone}</p>
                    </div>
                )}

                {payScale !== null && (
                    <div className="mt-6 w-full max-w-md bg-white p-4 rounded-lg shadow-md">
                        <h2 className="text-xl font-semibold mb-4">Pay Scale</h2>
                        <p><strong>Hourly Rate:</strong> ${payScale}</p>
                    </div>
                )}
            </div>
        </div>
    );
}
