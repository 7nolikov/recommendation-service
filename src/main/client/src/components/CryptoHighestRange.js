import React, { useState } from 'react';
import { fetchCryptoWithHighestRange } from '../services/cryptoService';

const CryptoHighestRange = () => {
  const [day, setDay] = useState('');
  const [highestRangeCrypto, setHighestRangeCrypto] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchCryptoWithHighestRange(day)
      .then(response => setHighestRangeCrypto(response.data))
      .catch(error => console.error(error));
  };

  return (
    <div className="max-w-md mx-auto my-10 p-5 bg-white rounded-lg shadow-xl">
      <h2 className="text-2xl font-bold text-center mb-6">Highest Normalized Range Crypto</h2>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <label htmlFor="date" className="font-medium">Select Date:</label>
        <input
          id="date"
          type="date"
          value={day}
          onChange={(e) => setDay(e.target.value)}
          className="border border-gray-300 rounded-md p-2"
          placeholder="YYYY-MM-DD"
        />
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        >
          Find
        </button>
      </form>
      {highestRangeCrypto && (
        <div className="mt-4 p-4 border-t border-gray-200">
          <p className="text-lg"><strong>Date:</strong> {day}</p>
          <p className="text-lg"><strong>Symbol:</strong> {highestRangeCrypto.symbol}</p>
          <p className="text-lg"><strong>Normalized Range:</strong> {highestRangeCrypto.normalizedRange}</p>
        </div>
      )}
    </div>

  );
};

export default CryptoHighestRange;
