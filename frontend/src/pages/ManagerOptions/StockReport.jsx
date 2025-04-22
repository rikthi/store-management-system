import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../AuthContext.jsx";
import { Bar } from "react-chartjs-2";
import { useNavigate } from "react-router-dom";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

export function StockReport() {
    const { user } = useAuth();
    const [inventories, setInventories] = useState([]);
    const [selectedInventory, setSelectedInventory] = useState(null);
    const [items, setItems] = useState([]);
    const [report, setReport] = useState(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchInventories = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/${user.storeId}/inventory/listAllInventories`);
                setInventories(response.data);
                if (response.data.length > 0) {
                    setSelectedInventory(response.data[0]);
                }
            } catch (error) {
                console.error("Failed to fetch inventories", error);
            }
        };
        fetchInventories();
    }, [user.storeId]);

    useEffect(() => {
        const fetchItemsAndReport = async () => {
            if (!selectedInventory) return;
            setLoading(true);
            try {
                const [itemsResponse, reportResponse] = await Promise.all([
                    axios.get(`http://localhost:8081/${user.storeId}/inventory/${selectedInventory.id}/listAllItems`),
                    axios.get(`http://localhost:8081/${user.storeId}/inventory/${selectedInventory.id}/stockReport`)
                ]);
                setItems(itemsResponse.data);
                setReport(reportResponse.data);
            } catch (error) {
                console.error("Failed to fetch items or stock report", error);
            } finally {
                setLoading(false);
            }
        };
        fetchItemsAndReport();
    }, [selectedInventory, user.storeId]);

    const generateColor = () => {
        const r = Math.floor(Math.random() * 200);
        const g = Math.floor(Math.random() * 230);
        const b = Math.floor(Math.random() * 250);
        return `rgba(${r}, ${g}, ${b}, 0.7)`;
    };

    const itemColors = items.reduce((acc, item) => {
        acc[item.name] = generateColor();
        return acc;
    }, {});

    const chartData = {
        labels: items.map(item => item.name),
        datasets: [
            {
                label: 'Quantity',
                data: items.map(item => item.quantity),
                backgroundColor: items.map(item => itemColors[item.name]),
            }
        ]
    };

    const chartOptions = {
        responsive: true,
        plugins: {
            legend: {
                position: 'right',
                labels: {
                    generateLabels: chart => {
                        return items.map(item => ({
                            text: item.name,
                            fillStyle: itemColors[item.name],
                            strokeStyle: itemColors[item.name],
                            lineWidth: 1,
                            hidden: false,
                            index: items.findIndex(i => i.name === item.name)
                        }));
                    }
                }
            },
            title: {
                display: true,
                text: `Stock Report for: ${selectedInventory?.category || ''}`,
                font: {
                    size: 14,
                    weight: 'bold',
                },
            },
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Items',
                    color: '#333',
                    font: { size: 14, weight: 'bold' }
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Quantity',
                    color: '#333',
                    font: { size: 14, weight: 'bold' }
                }
            }
        }
    };

    const now = new Date();
    const upcomingExpiries = items.filter(item => {
        const expiryDate = new Date(item.expirationDate);
        const daysLeft = Math.ceil((expiryDate - now) / (1000 * 60 * 60 * 24));
        return daysLeft <= 10;
    });

    return (
        <div
            className="min-h-screen bg-cover bg-center bg-no-repeat"
            style={{ backgroundImage: "url('../../src/assets/loginBg.jpg')" }}
        >
            <div className="bg-opacity-90 w-full py-20 shadow text-center">
                <h1 className="text-3xl font-bold text-white">Inventory Stock Report</h1>
            </div>

            <div className="flex">
                {/* Sidebar */}
                <div className="w-1/5 bg-white p-4 bg-opacity-80 rounded-lg shadow-md overflow-y-auto flex flex-col justify-between">
                    <div>
                        <h2 className="text-xl font-bold mb-4">Categories</h2>
                        <ul className="space-y-2">
                            {inventories.map((inv) => (
                                <li
                                    key={inv.id}
                                    onClick={() => setSelectedInventory(inv)}
                                    className={`cursor-pointer p-2 rounded hover:bg-blue-100 ${selectedInventory?.id === inv.id ? "bg-blue-200 font-semibold" : ""}`}
                                >
                                    {inv.category} {inv.hasStockIssue && <span className="text-red-500 ml-2">⚠️</span>}
                                </li>
                            ))}
                        </ul>
                    </div>

                    <button
                        onClick={() => navigate("/ManagerHome")}
                        className="mt-6 w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition"
                    >
                        Manager Home
                    </button>
                </div>

                {/* Chart + Report */}
                <div className="w-4/5 p-6 space-y-4">
                    <div className="bg-white bg-opacity-90 rounded-lg shadow-md p-4 overflow-y-auto max-h-[400px]">
                        {loading ? (
                            <p>Loading data...</p>
                        ) : (
                            <Bar data={chartData} options={chartOptions} />
                        )}
                    </div>

                    {report && (
                        <div className="bg-white bg-opacity-90 rounded-lg shadow-md p-4">
                            <h3 className="text-lg font-semibold mb-2">Inventory Stock Status</h3>
                            <p>
                                <strong>Under Minimum:</strong>{" "}
                                <span className={report.isUnderMinimum ? "text-red-600 font-semibold" : "text-green-600 font-semibold"}>
                                    {report.isUnderMinimum ? "Yes ❌" : "No ✅"}
                                </span>
                            </p>
                            <p>
                                <strong>Over Maximum:</strong>{" "}
                                <span className={report.isOverMaximum ? "text-red-600 font-semibold" : "text-green-600 font-semibold"}>
                                    {report.isOverMaximum ? "Yes ❌" : "No ✅"}
                                </span>
                            </p>
                        </div>
                    )}

                    <div className="bg-white bg-opacity-90 rounded-lg shadow-md p-4">
                        <h3 className="text-lg font-semibold mb-4">Items Nearing Expiry (≤ 10 days)</h3>
                        <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
                            {upcomingExpiries.map(item => {
                                const expiryDate = new Date(item.expirationDate);
                                const daysLeft = Math.ceil((expiryDate - now) / (1000 * 60 * 60 * 24));
                                return (
                                    <div key={item.id} className="bg-white text-center border-5 rounded-full p-6 shadow-md">
                                        <p className="font-bold text-lg">{item.name}</p>
                                        <p className={daysLeft <= 0 ? "text-red-600 font-semibold" : "text-yellow-600 font-semibold"}>
                                            {daysLeft <= 0 ? "Expired" : `${daysLeft} days left`}
                                        </p>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
