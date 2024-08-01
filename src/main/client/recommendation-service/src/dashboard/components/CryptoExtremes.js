import React, { useState, useEffect } from "react";
import {
  getAllCryptosSortedByNormalizedRange,
  getExtremes,
} from "../../services/api";
import {
  Button,
  Typography,
  Container,
  Alert,
  Grid,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Card,
  CardContent,
} from "@mui/material";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  Text,
} from "recharts";

const CryptoExtremes = () => {
  const [cryptoSymbol, setCryptoSymbol] = useState("");
  const [extremes, setExtremes] = useState(null);
  const [error, setError] = useState(null);
  const [cryptoList, setCryptoList] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const data = await getAllCryptosSortedByNormalizedRange();
      const uniqueSymbols = [...new Set(data.map((crypto) => crypto.symbol))];
      setCryptoList(uniqueSymbols);
    };

    fetchData();
  }, []);

  const handleSearch = async () => {
    if (!cryptoSymbol) {
      setError("Please select a cryptocurrency");
      return;
    }

    const data = await getExtremes(cryptoSymbol);
    if (data.error) {
      setError(data.error);
      setExtremes(null);
    } else {
      setExtremes(data);
      setError(null);
    }
  };

  const formatDataForChart = (extremes) => {
    if (!extremes) return [];

    return [
      {
        name: "Oldest",
        date: new Date(extremes.oldestTimestamp).toLocaleString(),
        price: extremes.oldestPrice,
        timestamp: extremes.oldestTimestamp,
      },
      { name: "Minimum", price: extremes.minPrice },
      { name: "Maximum", price: extremes.maxPrice },
      {
        name: "Newest",
        date: new Date(extremes.newestTimestamp).toLocaleString(),
        price: extremes.newestPrice,
        timestamp: extremes.newestTimestamp,
      },
    ];
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Extremes
      </Typography>
      <Grid container spacing={4}>
        <Grid item xs={12} md={2}>
          <FormControl variant="outlined" fullWidth margin="normal">
            <InputLabel id="crypto-select-label">Crypto Symbol</InputLabel>
            <Select
              labelId="crypto-select-label"
              value={cryptoSymbol}
              onChange={(e) => setCryptoSymbol(e.target.value)}
              label="Crypto Symbol"
            >
              {cryptoList.map((symbol) => (
                <MenuItem key={symbol} value={symbol}>
                  {symbol}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button
            variant="contained"
            color="primary"
            onClick={handleSearch}
            fullWidth
            style={{ marginTop: "16px" }}
          >
            Search
          </Button>
          {error && (
            <Alert severity="error" style={{ marginTop: "16px" }}>
              {error}
            </Alert>
          )}
        </Grid>
        <Grid item xs={12} md={10}>
          {extremes && (
            <Card>
              <CardContent>
                <ResponsiveContainer width="100%" height={250}>
                  <BarChart
                    data={formatDataForChart(extremes)}
                    layout="vertical"
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis type="number" />
                    <YAxis
                      type="category"
                      dataKey="name"
                      tick={{ fill: "#666" }}
                      width={100}
                    />
                    <Tooltip
                      formatter={(value, name, props) => {
                        const data = props.payload;
                        if (data.date) {
                          return [
                            `$${value.toFixed(2)}`,
                            `Date: ${new Date(
                              data.timestamp
                            ).toLocaleString()}`,
                          ];
                        }
                        return `$${value.toFixed(2)}`;
                      }}
                      labelFormatter={() => ""}
                      contentStyle={{ fontSize: "12px" }}
                    />
                    <Bar dataKey="price" fill="#1976d2" barSize={20} />
                  </BarChart>
                </ResponsiveContainer>
              </CardContent>
            </Card>
          )}
        </Grid>
      </Grid>
    </Container>
  );
};

export default CryptoExtremes;
