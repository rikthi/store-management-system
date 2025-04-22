import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx"; // Adjusted path

export function ViewEmployeeInfo() {
    const { user } = useAuth();
    const [info, setInfo] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchEmployee = async () => {
            setLoading(true);
            try {
                const response = await axios.post(
                    `http://localhost:8081/${user.storeId}/employees/getEmployee`,
                    { params: user.userId }
                );
                const emp = response.data;

                // Ensure proper date format for input[type="date"]
                emp.dateOfBirth = emp.dateOfBirth?.slice(0, 10) || "";

                setInfo(emp);
            } catch (err) {
                console.error("Error fetching employee:", err);
                setError("Failed to fetch employee details.");
            } finally {
                setLoading(false);
            }
        };
        fetchEmployee();
    }, [user.userId, user.storeId]);

    return (
        <div
            className="relative flex flex-col items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            <div className="absolute inset-0 bg-black opacity-60" />

            <div className="relative z-10 flex flex-col items-center bg-white bg-opacity-80 p-8 rounded-lg shadow-lg space-y-4 w-96">
                <h1 className="text-2xl font-bold text-gray-800">Employee Info</h1>

                {loading && <p className="text-blue-500">Loading...</p>}
                {error && <p className="text-red-500">{error}</p>}

                {info && (
                    <div className="space-y-2 text-left w-full">
                        <p><strong>ID:</strong> {info.id}</p>
                        <p><strong>Name:</strong> {info.name}</p>
                        <p><strong>Gender:</strong> {info.gender}</p>
                        <p><strong>Phone Number:</strong> {info.phoneNumber}</p>
                        <p><strong>Date of Birth:</strong> {info.dateOfBirth}</p>
                        <p><strong>Email Address:</strong> {info.emailAddress}</p>
                        <p><strong>Address:</strong> {info.address}</p>
                        <p><strong>Supervisor ID:</strong> {info.supervisorId ?? "N/A"}</p>
                    </div>
                )}
            </div>
        </div>
    );
}
