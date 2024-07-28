import React from "react";
import { createTheme, ThemeProvider, alpha } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Navbar from "./components/Navbar";
import CryptoList from "./components/CryptoList";
import CryptoExtremes from "./components/CryptoExtremes";
import HighestNormalizedRange from "./components/HighestNormalizedRange";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

function Dashboard() {
  const [mode, setMode] = React.useState("light");
  const defaultTheme = createTheme({ palette: { mode } });

  const toggleColorMode = () => {
    setMode((prev) => (prev === "dark" ? "light" : "dark"));
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Box sx={{ display: "flex", flexDirection: "column", height: "100vh" }}>
        <Navbar mode={mode} toggleColorMode={toggleColorMode} />
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            py: 4,
            px: 2,
            backgroundColor: alpha(defaultTheme.palette.background.default, 1),
            overflow: "auto",
          }}
        >
          <Grid container spacing={4} justifyContent="center">
            <Grid item xs={12} md={6}>
              <Paper elevation={3} sx={{ p: 2 }}>
                <CryptoExtremes />
              </Paper>
            </Grid>
            <Grid item xs={12} md={6}>
              <Paper elevation={3} sx={{ p: 2 }}>
                <HighestNormalizedRange />
              </Paper>
            </Grid>
            <Grid item xs={12}>
              <CryptoList />
            </Grid>
          </Grid>
        </Box>
      </Box>
    </ThemeProvider>
  );
}

export default Dashboard;
