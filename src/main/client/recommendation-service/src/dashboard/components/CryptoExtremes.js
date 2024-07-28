import React, { useState } from "react";
import { getExtremes } from "../../services/api";
import { Button, TextField, Typography, Container, Alert } from "@mui/material";

const CryptoExtremes = () => {
  const [cryptoSymbol, setCryptoSymbol] = useState("");
  const [extremes, setExtremes] = useState(null);
  const [error, setError] = useState(null);

  const handleSearch = async () => {
    const data = await getExtremes(cryptoSymbol);
    if (data.error) {
      setError(data.error);
    } else {
      setExtremes(data);
      setError(null);
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Cryptocurrency Extremes
      </Typography>
      <TextField
        label="Crypto Symbol"
        value={cryptoSymbol}
        onChange={(e) => setCryptoSymbol(e.target.value)}
        variant="outlined"
        fullWidth
        margin="normal"
      />
      <Button variant="contained" color="primary" onClick={handleSearch}>
        Search
      </Button>
      {error && <Alert severity="error">{error}</Alert>}
      {extremes && (
        <div>
          <Typography variant="h6">Oldest: {extremes.oldest}</Typography>
          <Typography variant="h6">Newest: {extremes.newest}</Typography>
          <Typography variant="h6">Min: {extremes.min}</Typography>
          <Typography variant="h6">Max: {extremes.max}</Typography>
        </div>
      )}
    </Container>
  );
};

export default CryptoExtremes;
