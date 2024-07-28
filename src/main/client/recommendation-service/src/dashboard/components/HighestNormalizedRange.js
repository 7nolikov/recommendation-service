import React, { useState } from "react";
import { findWithHighestNormalizedRange } from "../../services/api";
import { Button, TextField, Typography, Container, Alert } from "@mui/material";

const HighestNormalizedRange = () => {
  const [day, setDay] = useState("");
  const [crypto, setCrypto] = useState(null);
  const [error, setError] = useState(null);

  const handleSearch = async () => {
    const data = await findWithHighestNormalizedRange(day);
    if (data.error) {
      setError(data.error);
    } else {
      setCrypto(data);
      setError(null);
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Crypto with Highest Normalized Range
      </Typography>
      <TextField
        label="Date"
        type="date"
        value={day}
        onChange={(e) => setDay(e.target.value)}
        InputLabelProps={{ shrink: true }}
        variant="outlined"
        fullWidth
        margin="normal"
      />
      <Button variant="contained" color="primary" onClick={handleSearch}>
        Search
      </Button>
      {error && <Alert severity="error">{error}</Alert>}
      {crypto && (
        <Typography variant="h6">
          {crypto.symbol}: {crypto.normalizedRange}
        </Typography>
      )}
    </Container>
  );
};

export default HighestNormalizedRange;
