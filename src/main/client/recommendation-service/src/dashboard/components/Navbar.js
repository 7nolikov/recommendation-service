import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import ToggleColorMode from "./ToggleColorMode";
import Box from "@mui/material/Box";
import OptionsMenu from "./OptionsMenu";

const Navbar = ({ mode, toggleColorMode }) => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Crypto Dashboard
        </Typography>
        <Box>
          <ToggleColorMode
            mode={mode}
            toggleColorMode={toggleColorMode}
            data-screenshot="toggle-mode"
          />
          <OptionsMenu />
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
