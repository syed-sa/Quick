import { Routes, Route } from "react-router-dom";
import Header from "./components/layouts/Header";
import Footer from "./components/layouts/Footer";
import HomePage from "./pages/HomePage";
import AuthForm from "./components/auth/AuthForm";
import Favorites from "./pages/Favorites";
import { useState } from "react"

function App() {
  const [selectedCity, setSelectedCity] = useState("Mumbai");

  return (
    <div className="flex flex-col min-h-screen">
      <Header selectedCity={selectedCity} setSelectedCity={setSelectedCity} />
      <Routes>
        <Route path="/" element={<HomePage selectedCity={selectedCity} />} />
        <Route path="/auth" element={<AuthForm />} />
        <Route path="/favourites" element={<Favorites />} />
      </Routes>
      <Footer />
    </div>
  );
  
}
export default App;
