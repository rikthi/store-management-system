import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { WebsiteTitle } from './pages/WebsiteTitle.jsx';
import {LoginPage} from "./pages/LoginPage";
import {RegistrationPage} from "./pages/RegistrationPage";
import {CreateManager} from "./pages/CreateManager";
import {ManagerHome} from "./pages/ManagerHome.jsx";
import {HourlyEmployeeHome} from "./pages/HourlyEmployeeHome.jsx";
import {SalariedEmployeeHome} from "./pages/SalariedEmployeeHome.jsx";
import {ViewEmployeeInfo} from "./pages/Employee/ViewEmployeeInfo.jsx";
import {CustomerHome} from "./pages/CustomerHome";
import {AddCustomer} from "./pages/Employee/AddCustomer";
import {CreateReceipt} from "./pages/Employee/CreateReceipt.jsx";
import {AddHourlyEmployee} from "./pages/ManagerOptions/AddHourlyEmployee.jsx";
import {AddSalariedEmployee} from "./pages/ManagerOptions/AddSalariedEmployee.jsx";
import {ViewEmployees} from "./pages/ManagerOptions/ViewEmployees.jsx";
import {AddNewItem} from "./pages/ManagerOptions/AddNewItem.jsx";
import {ViewCategories} from "./pages/ManagerOptions/ViewCategories.jsx";
import {StockReport} from "./pages/ManagerOptions/StockReport";
import {ViewReceipts} from "./pages/Employee/ViewReceipts.jsx";
import "./index.css";
import {AuthProvider} from "./pages/AuthContext.jsx";
import {CreateStore} from "./pages/CreateStore.jsx";
import {EditHourlyEmployee} from "./pages/ManagerOptions/EditHourlyEmployee.jsx";
import {EditSalariedEmployee} from "./pages/ManagerOptions/EditSalariedEmployee.jsx";
import {VerifyAttendance} from "./pages/ManagerOptions/VerifyAttendance.jsx";
import {AddInventory} from "./pages/ManagerOptions/AddInventory.jsx";
import {EditInventory} from "./pages/ManagerOptions/EditInventory.jsx";
import {OrderItem} from "./pages/ManagerOptions/OrderItem.jsx";
import {StoreDetails} from "./pages/StoreDetails.jsx";
import {ViewCustomers} from "./pages/Employee/ViewCustomers.jsx";
import {EditManagerInformation} from "./pages/ManagerOptions/EditManagerInformation.jsx";






function App() {
    return (
        <AuthProvider>
            <div>
                <WebsiteTitle/>
                <Router>
                    <div className="min-h-screen bg-gray-100 text-gray-900">
                        <Routes>
                            <Route path="/" element={<LoginPage/>}/>
                            <Route path="/Register" element={<RegistrationPage/>}/>
                            <Route path="/CreateStore" element={<CreateStore/>}/>
                            <Route path="/CreateManager/:storeId" element={<CreateManager/>}/>
                            <Route path="/StoreDetails" element={<StoreDetails/>}/>
                            <Route path="/ManagerHome" element={<ManagerHome/>}/>
                            <Route path="/Hourlyemployee" element={<HourlyEmployeeHome/>}/>
                            <Route path="/Salariedemployee" element={<SalariedEmployeeHome/>}/>
                            <Route path="/Employee/AddCustomer" element={<AddCustomer/>}/>
                            <Route path="/Employee/CreateReceipt" element={<CreateReceipt/>}/>
                            <Route path="/Employee/ViewEmployeeInfo" element={<ViewEmployeeInfo/>}/>
                            <Route path="/Employee/ViewReceipts/:customerId" element={<ViewReceipts/>}/>
                            <Route path="/Employee/ViewCustomers" element={<ViewCustomers/>}/>
                            <Route path="/ManagerOptions/EditManagerInformation" element={<EditManagerInformation/>}/>
                            <Route path="/ManagerOptions/AddHourlyEmployee" element={<AddHourlyEmployee/>}/>
                            <Route path="/ManagerOptions/EditHourlyEmployee/:employeeId"
                                   element={<EditHourlyEmployee/>}/>
                            <Route path="/ManagerOptions/AddSalariedEmployee" element={<AddSalariedEmployee/>}/>
                            <Route path="/ManagerOptions/EditSalariedEmployee/:employeeId"
                                   element={<EditSalariedEmployee/>}/>
                            <Route path="/ManagerOptions/ViewEmployees" element={<ViewEmployees/>}/>
                            <Route path="/ManagerOptions/VerifyAttendance" element={<VerifyAttendance/>}/>
                            <Route path="/ManagerOptions/AddNewItem/:inventoryId/:category" element={<AddNewItem/>}/>
                            <Route path="/ManagerOptions/EditInventory/:inventoryId" element={<EditInventory/>}/>
                            <Route path="/ManagerOptions/OrderItem/:itemId" element={<OrderItem />} />
                            <Route path="/ManagerOptions/AddInventory" element={<AddInventory/>}/>
                            <Route path="/ManagerOptions/ViewCategories" element={<ViewCategories/>}/>
                            <Route path="/ManagerOptions/StockReport" element={<StockReport/>}/>

                        </Routes>
                    </div>
                </Router>
            </div>
        </AuthProvider>
    );
}
export default App;