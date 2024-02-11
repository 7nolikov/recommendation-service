import React, { useEffect, useState } from 'react';
import { fetchAllCryptosSortedByRange } from '../services/cryptoService';

const CryptoList = () => {
  const [cryptos, setCryptos] = useState([]);

  useEffect(() => {
    fetchAllCryptosSortedByRange()
      .then(response => setCryptos(response.data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Cryptocurrencies</h2>
      <ul className="list-disc pl-5">
        {cryptos.map((crypto, index) => (
          <li key={index} className="mb-2">
            {crypto.symbol}: {crypto.normalizedRange}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CryptoList;