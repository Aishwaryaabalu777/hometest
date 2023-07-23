import React, { useEffect, useMemo, useState } from 'react'
import PropTypes from 'prop-types';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import Drawer from '@mui/material/Drawer';
import IconButton from '@mui/material/IconButton';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import List from '@mui/material/List';

import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MailIcon from '@mui/icons-material/Mail';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Link, useNavigate } from 'react-router-dom';
import '../../App.css'
import { Dashboard, Logout } from '@mui/icons-material';
import axios from 'axios';
import { BASE_URL, LOGOUT } from '../../utils/ApplicationUrl';

const drawerWidth = 240;



export default function SideNav(props) {
  let navigate= useNavigate()
  const [open, setOpen] = useState(false);
  const [error, setError] = React.useState("");
  const [data, setData] = useState([]);
    const { window } = props;
    const [mobileOpen, setMobileOpen] =useState(false);
  
    const handleDrawerToggle = () => {
      setMobileOpen(!mobileOpen);
    };
    const handleLogout = () => {
      axios
        .post(BASE_URL + LOGOUT)
        .then(function (response) {
          // handle success
          console.log(response.data);
          if (response.status == 200) {
            if(typeof window !== 'undefined') {
              // localStorage access here
              localStorage.removeItem("token");
              localStorage.removeItem("user");
            }
          
            navigate("/");
          } else {
            setError("Something went wrong, please try again after some time.");
            setOpen(true);
          }
        })
        .catch(function (error) {
          // handle error
          console.log(error);
        })
        .finally(function () {
          // always executed
        });
    }
    useEffect(() => {
      if(typeof window !== 'undefined') {
       
        if (localStorage.getItem("token") == null) {
          navigate("/");
        }
      }
    
    }, []);
  
    const drawer = (
      <div>
      <Toolbar  style={{backgroundColor:'#1976D2'}}>
        <div>
          <h5>Zuzu Hospitality</h5>
        </div>
      </Toolbar>
      <Divider />
      <List>
        {['Dashboard','Logout' ].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton 
              component={Link} 
              to={index === 0 ? '/dashboard' : ''}
              onClick={index === 1 ? handleLogout : null}
            >
              <ListItemIcon>
                {index % 2 === 0 ? <Dashboard /> : <Logout />}
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </div>
    );
  
    const container = window !== undefined ? () => window().document.body : undefined;
  
    return (
      <Box sx={{ display: 'flex' }}>
        <CssBaseline />
        <AppBar
          position="fixed"
          sx={{
            width: { sm: `calc(100% - ${drawerWidth}px)` },
            ml: { sm: `${drawerWidth}px` },
          }}
        >
          <Toolbar>
            <IconButton
              color="inherit"
              aria-label="open drawer"
              edge="start"
              onClick={handleDrawerToggle}
              sx={{ mr: 2, display: { sm: 'none' } }}
            >
              <MenuIcon />
            </IconButton>
            
          </Toolbar>
        </AppBar>
        <Box
          component="nav"
          sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
          aria-label="mailbox folders"
        >
       
          <Drawer
            container={container}
            variant="temporary"
            open={mobileOpen}
            onClose={handleDrawerToggle}
           
            sx={{
              display: { xs: 'block', sm: 'none' },
              '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
            }}
          >
            {drawer}
          </Drawer>
          <Drawer
            variant="permanent"
            sx={{
              display: { xs: 'none', sm: 'block' },
              '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
            }}
            open
          >
            {drawer}
          </Drawer>
        </Box>
        <Box
          component="main"
          sx={{ flexGrow: 1, p: 3, width: { sm: `calc(100% - ${drawerWidth}px)` } }}
        >
          <Toolbar />
        
        </Box>
      </Box>
    );
  }
  
  SideNav.propTypes = {
    
    window: PropTypes.func,
  };
