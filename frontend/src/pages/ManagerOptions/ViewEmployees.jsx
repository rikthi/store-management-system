import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext.jsx";

export function ViewEmployees() {
    const { user } = useAuth();
    const [employees, setEmployees] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [filterType, setFilterType] = useState("ALL");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchEmployees = async () => {
            try {
                let response;
                if (filterType === "ALL") {
                    response = await axios.get(`http://localhost:8081/${user.storeId}/employees`);
                } else if (filterType === "SALARIED_EMPLOYEE") {
                    response = await axios.get(`http://localhost:8081/${user.storeId}/employees/listSalariedEmployees`);
                } else if (filterType === "HOURLY_EMPLOYEE") {
                    response = await axios.get(`http://localhost:8081/${user.storeId}/employees/listHourlyEmployees`);
                }
                setEmployees(response.data);
            } catch (error) {
                console.error("Fetch error:", error);
                setEmployees([]);
            }
        };
        fetchEmployees();
    }, [filterType, user.storeId]);

    const filtered = employees.filter((emp) => {
        const id = emp.employee ? emp.employee.id : emp.id;
        return id.toString().includes(searchTerm);
    });

    const handleViewDetails = (emp) => {
        const id = emp.employee ? emp.employee.id : emp.id;
        if (filterType === "SALARIED_EMPLOYEE") {
            navigate(`/ManagerOptions/EditSalariedEmployee/${id}`);
        } else if (filterType === "HOURLY_EMPLOYEE") {
            navigate(`/ManagerOptions/EditHourlyEmployee/${id}`);
        } else {
            navigate(`/ManagerOptions/EditEmployee/${id}`);
        }
    };

    return (
        <div className="relative flex flex-col items-center justify-start min-h-screen bg-cover bg-center p-10"
             style={{ backgroundImage: "url('../src/assets/loginBg.jpg')" }}>
            <div className="absolute inset-0 bg-black opacity-50 -z-10" />
            <h1 className="text-3xl font-bold text-white mt-20 mb-6">View Employees</h1>

            <div className="flex flex-wrap justify-center items-center gap-4 mb-6 w-full max-w-6xl">
                <input
                    type="text"
                    placeholder="Search by Employee ID"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="p-2 border rounded w-[60%] min-w-[250px]"
                />
                <div className="flex gap-2">
                    {['ALL', 'HOURLY_EMPLOYEE', 'SALARIED_EMPLOYEE'].map(type => (
                        <button
                            key={type}
                            onClick={() => setFilterType(type)}
                            className={`px-4 py-2 rounded-lg shadow-lg transition-transform duration-200 text-white ${
                                type === 'HOURLY_EMPLOYEE' ? 'bg-green-500 hover:bg-green-600'
                                    : type === 'SALARIED_EMPLOYEE' ? 'bg-yellow-500 hover:bg-yellow-600'
                                        : 'bg-blue-500 hover:bg-blue-600'
                            }`}
                        >
                            {type.replace('_', ' ')}
                        </button>
                    ))}
                </div>
            </div>

            <div className="w-full max-w-6xl overflow-x-auto bg-white bg-opacity-90 p-4 rounded shadow">
                <table className="w-full table-auto text-sm text-left">
                    <thead className="bg-gray-200">
                    <tr>
                        <th className="px-4 py-2">ID</th>
                        <th className="px-4 py-2">Name</th>
                        <th className="px-4 py-2">Role</th>
                        <th className="px-4 py-2">Phone</th>
                        <th className="px-4 py-2">Email</th>
                        <th className="px-4 py-2">DOB</th>
                        <th className="px-4 py-2">Address</th>
                        <th className="px-4 py-2">Supervisor ID</th>
                        {filterType === "SALARIED_EMPLOYEE" && <th className="px-4 py-2">Salary</th>}
                        {filterType === "HOURLY_EMPLOYEE" && <th className="px-4 py-2">Pay Scale</th>}
                        {filterType !== "ALL" && <th className="px-4 py-2">Action</th>}
                    </tr>
                    </thead>
                    <tbody>
                    {filtered.map((emp, idx) => {
                        const e = emp.employee || emp;
                        return (
                            <tr key={e.id || idx} className="border-t hover:bg-gray-50">
                                <td className="px-4 py-2">{e.id}</td>
                                <td className="px-4 py-2">{e.name}</td>
                                <td className="px-4 py-2">{emp.role || filterType}</td>
                                <td className="px-4 py-2">{e.phoneNumber}</td>
                                <td className="px-4 py-2">{e.emailAddress}</td>
                                <td className="px-4 py-2">{e.dateOfBirth || "N/A"}</td>
                                <td className="px-4 py-2">{e.address}</td>
                                <td className="px-4 py-2">{e.supervisorId ?? "N/A"}</td>
                                {filterType === "SALARIED_EMPLOYEE" && (
                                    <td className="px-4 py-2">{emp.salary}</td>
                                )}
                                {filterType === "HOURLY_EMPLOYEE" && (
                                    <td className="px-4 py-2">{emp.payScale}</td>
                                )}
                                {filterType !== "ALL" && (
                                    <td className="px-4 py-2">
                                        <button
                                            onClick={() => handleViewDetails(emp)}
                                            className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-800"
                                        >
                                            Edit
                                        </button>
                                    </td>
                                )}
                            </tr>
                        );
                    })}
                    {filtered.length === 0 && (
                        <tr>
                            <td colSpan="10" className="text-center py-4 text-gray-500">
                                No employees found.
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
