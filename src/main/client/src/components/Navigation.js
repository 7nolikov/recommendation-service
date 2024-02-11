import React from 'react';
import { NavLink } from 'react-router-dom';

const Navigation = () => {
  return (
    <nav className="bg-blue-600 shadow-lg">
      <div className="max-w-6xl mx-auto px-4">
        <div className="flex justify-between">
          <div className="flex space-x-7">
            <div>
              {/* Website Logo */}
              <NavLink to="/" className="flex items-center py-4 px-2">
                <span className="font-semibold text-white text-lg">Crypto Dashboard</span>
              </NavLink>
            </div>
            {/* Primary Navbar items */}
            <div className="hidden md:flex items-center space-x-1">
              <NavLink to="/" className="py-4 px-2 text-blue-200 hover:text-white transition duration-300">
                Cryptocurrencies
              </NavLink>
              <NavLink to="/extremes" className="py-4 px-2 text-blue-200 hover:text-white transition duration-300">
                Crypto Extremes
              </NavLink>
              <NavLink to="/highest-range" className="py-4 px-2 text-blue-200 hover:text-white transition duration-300">
                Highest Range Crypto
              </NavLink>
            </div>
          </div>
          {/* Secondary Navbar items, Mobile menu button here if needed */}
        </div>
      </div>
    </nav>
  );
};

export default Navigation;
