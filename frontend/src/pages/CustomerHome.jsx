import { useNavigate } from "react-router-dom";
import React from "react";


export function CustomerHome() {
    const navigate = useNavigate();


    return (
        <div
            className="relative flex flex-col items-center justify-center h-screen bg-cover bg-center"
            style={{ backgroundImage: "url('src/assets/loginBg.jpg')" }}
        >
            {/* Overlay for better readability */}
            <div className="absolute inset-0 bg-black opacity-50"></div>

            {/* Content container positioned above the overlay */}
            <div className="relative z-10 flex flex-col items-center justify-center space-y-6 bg-white bg-opacity-80 p-8 rounded-lg shadow-lg">
                <h1 className="text-3xl font-bold text-gray-800">Customer Dashboard</h1>

                {/* Buttons */}
                <div className="space-y-4 w-80">
                    <button
                        onClick={() => navigate("/Customer/ViewCustomerInfo")}
                        className="w-full bg-green-500 text-white p-3 rounded-lg hover:bg-green-600 transition"
                    >
                        View Personal Information
                    </button>

                    <button
                        onClick={() => navigate("/Customer/ViewReceipts")}
                        className="w-full bg-blue-500 text-white p-3 rounded-lg hover:bg-blue-600 transition"
                    >
                        View Receipts
                    </button>
                    <button
                        onClick={() => navigate("/")}
                        className="btn-primary"
                    >
                        Logout
                    </button>
                </div>
            </div>
        </div>
    );
}
