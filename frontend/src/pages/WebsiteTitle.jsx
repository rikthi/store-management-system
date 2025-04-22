import React from 'react';
import logo from '../assets/webIcon.png';

export function WebsiteTitle() {
    return (
        <header className="fixed top-0 left-0 w-full flex items-center p-4 bg-gray-200 bg-opacity-10 z-50">
            <img src={logo} alt="Website Logo" className="h-10 mr-4" />
            <h1 className="text-white text-xl font-bold">Store Management System</h1>
        </header>
    );
}