import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext.jsx";

export function ViewCustomers() {
    const { user } = useAuth();
    const [customers, setCustomers] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/${user.storeId}/customer/get/all`);
                setCustomers(response.data);
            } catch (error) {
                console.error("Failed to fetch customers:", error);
            }
        };
        fetchCustomers();
    }, [user.storeId]);

    const filteredCustomers = customers.filter((customer) =>
        customer.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        customer.id.toString().includes(searchTerm)
    );

    const handleViewReceipts = (customerId) => {
        navigate(`/Employee/ViewReceipts/${customerId}`);
    };
    const handleRemoveCustomer = async (customerId) => {
        try {
            await axios.delete(`http://localhost:8081/${user.storeId}/customer/delete`, {
               data: { id: customerId }
            });
            setCustomers((prev) => prev.filter((c) => c.id !== customerId));
        } catch (error) {
            console.error("Failed to remove customer:", error);
        }
    };


    return (
        <div
            className="relative flex flex-col items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            <div className="max-w-6xl mx-auto bg-white p-6 rounded shadow">
                <h1 className="text-2xl font-bold mb-4">Customer List</h1>

                <input
                    type="text"
                    placeholder="Search by ID or name..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="w-full mb-4 p-2 border rounded"
                />

                <table className="w-full table-auto text-left">
                    <thead className="bg-gray-200">
                    <tr>
                        <th className="px-4 py-2">ID</th>
                        <th className="px-4 py-2">Name</th>
                        <th className="px-4 py-2">Email</th>
                        <th className="px-4 py-2">Membership Type</th>
                        <th className="px-4 py-2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredCustomers.map((customer) => (
                        <tr key={customer.id} className="border-t hover:bg-gray-50">
                            <td className="px-4 py-2">{customer.id}</td>
                            <td className="px-4 py-2">{customer.name}</td>
                            <td className="px-4 py-2">{customer.email}</td>
                            <td className="px-4 py-2">{customer.membershipType}</td>
                            <td className="px-4 py-2 flex gap-2">
                                <button
                                    onClick={() => handleViewReceipts(customer.id)}
                                    className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                                >
                                    View Receipts
                                </button>
                                <button
                                    onClick={() => handleRemoveCustomer(customer.id)}
                                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 ml-2"
                                >
                                    Remove
                                </button>
                            </td>
                        </tr>
                    ))}
                    {filteredCustomers.length === 0 && (
                        <tr>
                            <td colSpan="5" className="text-center py-4 text-gray-500">
                                No customers found.
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}