import axios from "axios";

const API_BASE_URL =
  "http://localhost:8080/api/recommendation-service/v1/crypto";

const handleError = (error) => {
  if (error.response) {
    // The request was made and the server responded with a status code
    // that falls out of the range of 2xx
    console.error("Response error:", error.response.data);
    return { error: error.response.data };
  } else if (error.request) {
    // The request was made but no response was received
    console.error("Request error:", error.request);
    return { error: "No response received from the server" };
  } else {
    // Something happened in setting up the request that triggered an Error
    console.error("General error:", error.message);
    return { error: error.message };
  }
};

export const getAllCryptosSortedByNormalizedRange = async () => {
  try {
    const response = await axios.get(
      `${API_BASE_URL}/sorted-by-normalized-range`
    );
    return response.data;
  } catch (error) {
    return handleError(error);
  }
};

export const getExtremes = async (cryptoSymbol) => {
  try {
    const response = await axios.get(
      `${API_BASE_URL}/extremes/${cryptoSymbol}`
    );
    return response.data;
  } catch (error) {
    return handleError(error);
  }
};

export const findWithHighestNormalizedRange = async (day) => {
  try {
    const response = await axios.get(
      `${API_BASE_URL}/highest-normalized-range`,
      {
        params: { day },
      }
    );
    return response.data;
  } catch (error) {
    return handleError(error);
  }
};
