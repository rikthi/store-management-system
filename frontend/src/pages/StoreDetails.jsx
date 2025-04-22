import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "./AuthContext.jsx";

export function StoreDetails() {
    const { user } = useAuth();
    const [store, setStore] = useState(null);
    const [manager, setManager] = useState(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchDetails = async () => {
            try {
                const [storeRes, managerRes] = await Promise.all([
                    axios.get(`http://localhost:8081/stores/get/${user.storeId}`),
                    axios.get(`http://localhost:8081/${user.storeId}/employees/get/manager`)
                ]);
                setStore(storeRes.data);
                setManager(managerRes.data);
            } catch (err) {
                setError("Failed to fetch store or manager details.");
            } finally {
                setLoading(false);
            }
        };
        fetchDetails();
    }, [user.storeId]);

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
             style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}>
            <div className="absolute inset-0 bg-black opacity-50 -z-10"></div>
            <div className="relative z-10 bg-white bg-opacity-90 p-8 rounded-lg shadow-lg w-full max-w-md">
                <h1 className="text-2xl font-bold text-center mb-6 text-gray-800">Store Details</h1>

                {loading && <p className="text-blue-500 text-center">Loading...</p>}
                {error && <p className="text-red-500 text-center">{error}</p>}

                {store && (
                    <div className="space-y-4 text-gray-800">
                        <div><strong>Store ID:</strong> {store.id}</div>
                        <div><strong>Store Name:</strong> {store.name}</div>
                        <div><strong>Address:</strong> {store.address}</div>
                        <div><strong>Manager Name:</strong> {manager?.employee?.name || "N/A"}</div>
                        <div><strong>Manager ID:</strong> {manager?.employee?.id || "N/A"}</div>
                    </div>
                )}
            </div>
        </div>
    );
}
