import React, { useState } from 'react';
import { fetchCryptoExtremes } from '../services/cryptoService';

const CryptoExtremes = () => {
  const [symbol, setSymbol] = useState('');
  const [extremes, setExtremes] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchCryptoExtremes(symbol)
      .then(response => setExtremes(response.data))
      .catch(error => console.error(error));
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Crypto Extremes</h2>
      <form onSubmit={handleSubmit} className="mb-4">
        <input
          type="text"
          value={symbol}
          onChange={e => setSymbol(e.target.value)}
          className="border border-gray-300 rounded-md shadow-sm py-2 px-3 mr-2"
          placeholder="Enter Crypto Symbol"
        />
        <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
          Find
        </button>
      </form>
      {extremes && (
        <div className="bg-gray-100 p-4 rounded-md">
          <p>Oldest: {extremes.oldest}</p>
          <p>Newest: {extremes.newest}</p>
          <p>Min: {extremes.min}</p>
          <p>Max: {extremes.max}</p>
        </div>
      )}
    </div>
  );
};

export default CryptoExtremes;