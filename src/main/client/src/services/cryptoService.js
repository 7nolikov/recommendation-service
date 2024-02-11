import axios from 'axios';

const API_BASE_URL = '/v1/crypto';

export const fetchAllCryptosSortedByRange = () => {
  return axios.get(`${API_BASE_URL}/sorted-by-normalized-range`);
};

export const fetchCryptoExtremes = (symbol) => {
  return axios.get(`${API_BASE_URL}/extremes/${symbol}`);
};

export const fetchCryptoWithHighestRange = (day) => {
  return axios.get(`${API_BASE_URL}/highest-normalized-range/${day}`);
};