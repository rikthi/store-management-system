import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";

export function VerifyAttendance() {
    const { user } = useAuth();
    const [attendances, setAttendances] = useState([]);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [verifyingId, setVerifyingId] = useState(null);

    useEffect(() => {
        const fetchAttendance = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/${user.storeId}/attendance`);
                setAttendances(response.data);
            } catch (err) {
                setError("Failed to load attendance records.");
            }
        };
        fetchAttendance();
    }, [user.storeId]);

    const handleVerify = async (attendanceId) => {
        const attendance = attendances.find(a => a.id === attendanceId);
        if (!attendance) return;

        const updatedAttendance = {
            ...attendance,
            isVerified: true,
            verifierId: user.userId
        };

        setVerifyingId(attendanceId);
        try {
            await axios.put(
                `http://localhost:8081/${user.storeId}/attendance/verify`,
                updatedAttendance
            );

            setSuccess(`Attendance ${attendanceId} verified successfully!`);
            setAttendances((prev) =>
                prev.map((a) =>
                    a.id === attendanceId ? { ...updatedAttendance } : a
                )
            );
        } catch (err) {
            setError("Verification failed.");
        } finally {
            setVerifyingId(null);
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
             style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}>
            <h1 className="text-2xl font-bold mb-4 text-white">Verify Attendance</h1>

            {error && <p className="text-red-500 mb-4">{error}</p>}
            {success && <p className="text-green-600 mb-4">{success}</p>}

            <div className="overflow-x-auto bg-white bg-opacity-90 rounded shadow p-4">
                <table className="w-full table-auto text-sm text-left">
                    <thead className="bg-gray-200">
                    <tr>
                        <th className="px-4 py-2">ID</th>
                        <th className="px-4 py-2">Employee ID</th>
                        <th className="px-4 py-2">Punch In</th>
                        <th className="px-4 py-2">Punch Out</th>
                        <th className="px-4 py-2">Verifier ID</th>
                        <th className="px-4 py-2">Verified</th>
                        <th className="px-4 py-2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {attendances.map((a) => (
                        <tr key={a.id} className="border-t hover:bg-gray-50">
                            <td className="px-4 py-2">{a.id}</td>
                            <td className="px-4 py-2">{a.employeeId}</td>
                            <td className="px-4 py-2">{a.punchInTime}</td>
                            <td className="px-4 py-2">{a.punchOutTime || "N/A"}</td>
                            <td className="px-4 py-2">{a.verifierId || "N/A"}</td>
                            <td className="px-4 py-2">{a.isVerified ? "✅" : "❌"}</td>
                            <td className="px-4 py-2">
                                {!a.isVerified && (
                                    <button
                                        onClick={() => handleVerify(a.id)}
                                        disabled={verifyingId === a.id}
                                        className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-700 disabled:opacity-50"
                                    >
                                        {verifyingId === a.id ? "Verifying..." : "Verify"}
                                    </button>
                                )}
                            </td>
                        </tr>
                    ))}
                    {attendances.length === 0 && (
                        <tr>
                            <td colSpan="7" className="text-center py-4 text-gray-500">
                                No attendance records found.
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
