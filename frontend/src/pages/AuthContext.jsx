import { createContext, useContext, useState } from "react";

// Create the AuthContext
const AuthContext = createContext();

// Provide user data throughout the app
export function AuthProvider({ children }) {
    const [user, setUser] = useState(null); // Store logged-in user info

    return (
        <AuthContext.Provider value={{ user, setUser }}>
            {children}
        </AuthContext.Provider>
    );
}

// Custom hook to access AuthContext
export function useAuth() {
    return useContext(AuthContext);
}