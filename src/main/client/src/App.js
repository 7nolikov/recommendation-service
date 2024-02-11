import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import CryptoList from './components/CryptoList';
import CryptoExtremes from './components/CryptoExtremes';
import CryptoHighestRange from './components/CryptoHighestRange';

function App() {
  return (
    <Router>
      <div className="flex flex-col min-h-screen bg-gray-100 text-gray-900">
        <Navigation />
        <main className="flex flex-1 justify-center items-start pt-10">
          <div className="w-full max-w-2xl px-4">
            <Routes>
              <Route exact path="/" element={<CryptoList />} />
              <Route path="/extremes" element={<CryptoExtremes />} />
              <Route path="/highest-range" element={<CryptoHighestRange />} />
            </Routes>
          </div>
        </main>
      </div>
    </Router>
  );
}

export default App;