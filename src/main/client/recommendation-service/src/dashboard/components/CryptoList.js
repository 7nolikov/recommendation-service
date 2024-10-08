import React, { useState, useEffect } from "react";
import { getAllCryptosSortedByNormalizedRange } from "../../services/api";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  CircularProgress,
  Box,
  Paper,
  TableSortLabel,
} from "@mui/material";

const CryptoList = () => {
  const [cryptos, setCryptos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [order, setOrder] = useState("asc");
  const [orderBy, setOrderBy] = useState("normalizedPrice");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getAllCryptosSortedByNormalizedRange();
        setCryptos(data);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleRequestSort = (property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const sortedCryptos = cryptos.sort((a, b) => {
    if (order === "asc") {
      return a[orderBy] < b[orderBy] ? -1 : 1;
    } else {
      return a[orderBy] > b[orderBy] ? -1 : 1;
    }
  });

  const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    return new Intl.DateTimeFormat("en-GB", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
    }).format(date);
  };

  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        height="100vh"
      >
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Typography variant="h6" color="error" align="center">
        Error: {error}
      </Typography>
    );
  }

  return (
    <>
      <Typography variant="h4" gutterBottom align="center">
        Cryptocurrencies
      </Typography>
      <TableContainer
        component={Paper}
        style={{
          margin: "20px auto",
          maxWidth: "40%",
          height: "35vh",
          overflow: "auto",
        }}
      >
        <Table stickyHeader size="small" style={{ tableLayout: "fixed" }}>
          <TableHead>
            <TableRow style={{ backgroundColor: "#f5f5f5" }}>
              <TableCell style={{ width: "15%", paddingRight: 0 }}>
                <TableSortLabel
                  active={orderBy === "symbol"}
                  direction={orderBy === "symbol" ? order : "asc"}
                  onClick={() => handleRequestSort("symbol")}
                >
                  Symbol
                </TableSortLabel>
              </TableCell>
              <TableCell
                align="right"
                style={{ width: "25%", paddingRight: 16 }}
              >
                <TableSortLabel
                  active={orderBy === "price"}
                  direction={orderBy === "price" ? order : "asc"}
                  onClick={() => handleRequestSort("price")}
                >
                  Price
                </TableSortLabel>
              </TableCell>
              <TableCell
                align="right"
                style={{ width: "30%", paddingRight: 16 }}
              >
                <TableSortLabel
                  active={orderBy === "timestamp"}
                  direction={orderBy === "timestamp" ? order : "asc"}
                  onClick={() => handleRequestSort("timestamp")}
                >
                  Timestamp
                </TableSortLabel>
              </TableCell>
              <TableCell
                align="right"
                style={{ width: "30%", paddingRight: 16 }}
              >
                <TableSortLabel
                  active={orderBy === "normalizedPrice"}
                  direction={orderBy === "normalizedPrice" ? order : "desc"}
                  onClick={() => handleRequestSort("normalizedPrice")}
                >
                  Normalized Price
                </TableSortLabel>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {sortedCryptos.map((crypto) => (
              <TableRow key={`${crypto.symbol}-${crypto.timestamp}`}>
                <TableCell component="th" scope="row">
                  {crypto.symbol}
                </TableCell>
                <TableCell align="right">{crypto.price.toFixed(2)}</TableCell>
                <TableCell align="right">
                  {formatTimestamp(crypto.timestamp)}
                </TableCell>
                <TableCell align="right">
                  {crypto.normalizedPrice.toFixed(10)}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};

export default CryptoList;
