import React, { useState } from "react";
import { findWithHighestNormalizedRange } from "../../services/api";
import {
  Button,
  Typography,
  Container,
  Alert,
  Grid,
  Card,
  CardContent,
  TextField,
} from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { format } from "date-fns";
import { enCA } from "date-fns/locale"; // Import the Canadian English locale which uses the YYYY-MM-DD format

const HighestNormalizedRange = () => {
  const [day, setDay] = useState(() => format(new Date(), "yyyy-MM-dd"));
  const [crypto, setCrypto] = useState(null);
  const [error, setError] = useState(null);

  const handleSearch = async () => {
    try {
      const data = await findWithHighestNormalizedRange(day);
      if (data.error) {
        setError(data.error);
        setCrypto(null);
      } else {
        setCrypto(data);
        setError(null);
      }
    } catch (err) {
      setError("An unexpected error occurred. Please try again later.");
      setCrypto(null);
    }
  };

  return (
    <Container style={{ height: "35vh" }}>
      <Typography variant="h4" gutterBottom>
        Crypto with Highest Normalized Range
      </Typography>
      <Grid container spacing={4}>
        <Grid item xs={12} md={4}>
          <LocalizationProvider dateAdapter={AdapterDateFns} locale={enCA}>
            <DatePicker
              label="Date"
              value={day}
              inputFormat="yyyy-MM-dd"
              onChange={(newValue) => {
                if (newValue) {
                  setDay(format(new Date(newValue), "yyyy-MM-dd"));
                }
              }}
              renderInput={(params) => <TextField {...params} fullWidth />}
            />
          </LocalizationProvider>
          <Button
            variant="contained"
            color="primary"
            onClick={handleSearch}
            fullWidth
            style={{ marginTop: "16px" }}
          >
            Search
          </Button>
        </Grid>
        <Grid item xs={12} md={8}>
          {error ? (
            <Alert severity="error" style={{ marginTop: "16px" }}>
              {typeof error === "string" ? error : JSON.stringify(error)}
            </Alert>
          ) : (
            crypto && (
              <Card>
                <CardContent>
                  <Container width="100%" height={250}>
                    <Typography variant="h6">
                      Symbol: {crypto.symbol}
                    </Typography>
                    <Typography variant="h6">
                      Price: ${crypto.price.toFixed(2)}
                    </Typography>
                    <Typography variant="h6">Timestamp: {day}</Typography>
                    <Typography variant="h6">
                      Normalized Price: {crypto.normalizedPrice.toFixed(10)}
                    </Typography>
                  </Container>
                </CardContent>
              </Card>
            )
          )}
        </Grid>
      </Grid>
    </Container>
  );
};

export default HighestNormalizedRange;
