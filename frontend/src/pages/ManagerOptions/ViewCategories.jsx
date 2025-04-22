import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext.jsx";

export function ViewCategories() {
    const { user } = useAuth();
    const [categories, setCategories] = useState([]);
    const [selectedInventory, setSelectedInventory] = useState(null);
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/${user.storeId}/inventory/listAllInventories`);
                setCategories(response.data);
                if (response.data.length > 0) {
                    setSelectedInventory(response.data[0]);
                }
            } catch (err) {
                console.error("Failed to load categories", err);
            }
        };
        fetchCategories();
    }, [user.storeId]);

    useEffect(() => {
        if (!selectedInventory) return;

        const fetchItems = async () => {
            setLoading(true);
            try {
                const response = await axios.get(
                    `http://localhost:8081/${user.storeId}/inventory/${selectedInventory.id}/listAllItems`
                );
                setItems(response.data);
            } catch (err) {
                console.error("Failed to load items", err);
            } finally {
                setLoading(false);
            }
        };

        fetchItems();
    }, [selectedInventory]);

    const handleOrder = (itemId) => {
        navigate(`/ManagerOptions/OrderItem/${itemId}`);
    };

    const handleAddNewItem = () => {
        navigate(`/ManagerOptions/AddNewItem/${selectedInventory.id}/${selectedInventory.category}`);
    };

    return (
        <div
            className="flex min-h-screen pt-24 bg-gray-100 bg-cover bg-center bg-no-repeat"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            {/* Sidebar */}
            <div className="w-1/5 bg-white bg-opacity-80 rounded-lg shadow-md p-4">
                <h2 className="text-xl font-bold mb-4">Categories</h2>
                <ul className="space-y-2">
                    {categories.map((inv) => (
                        <li
                            key={inv.id}
                            onClick={() => setSelectedInventory(inv)}
                            className={`p-2 rounded cursor-pointer hover:bg-blue-100 ${
                                selectedInventory?.id === inv.id ? "bg-blue-200 font-semibold" : ""
                            }`}
                        >
                            {inv.category}
                        </li>
                    ))}
                </ul>

                <button
                    onClick={handleAddNewItem}
                    disabled={!selectedInventory}
                    className="mt-6 w-full bg-green-500 text-white py-2 rounded hover:bg-green-600 transition"
                >
                    Add New Item
                </button>

                <button
                    onClick={() => navigate(`/ManagerOptions/EditInventory/${selectedInventory.id}/`)}
                    disabled={!selectedInventory}
                    className="mt-2 w-full bg-yellow-500 text-white py-2 rounded hover:bg-yellow-600 transition"
                >
                    Edit Inventory
                </button>

                <button
                    onClick={() => navigate("/ManagerHome")}
                    className="mt-2 w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition"
                >
                    Home Page
                </button>
            </div>

            {/* Item Table */}
            <div className="w-4/5 p-6">
                <h2 className="text-2xl font-bold mb-4">
                    Items in {selectedInventory?.category || "Category"}
                </h2>
                {loading ? (
                    <p>Loading items...</p>
                ) : (
                    <table className="w-full table-auto bg-white bg-opacity-90 rounded shadow">
                        <thead className="bg-gray-200" align="left">
                        <tr>
                            <th className="p-2">Name</th>
                            <th className="p-2">Price</th>
                            <th className="p-2">Discount</th>
                            <th className="p-2">In Stock</th>
                            <th className="p-2">Expiration Date</th>
                            <th className="p-2">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {items.length > 0 ? (
                            items.map((item) => (
                                <tr key={item.id} className="border-t">
                                    <td className="p-2">{item.name}</td>
                                    <td className="p-2">${item.price}</td>
                                    <td className="p-2">{item.discountPercentage}%</td>
                                    <td className="p-2">{item.quantity}</td>
                                    <td className="p-2">{item.expirationDate}</td>
                                    <td className="p-2">
                                        <button
                                            onClick={() => handleOrder(item.id)}
                                            className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                                        >
                                            Order
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="6" className="text-center py-4 text-gray-500">
                                    No items found.
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}
