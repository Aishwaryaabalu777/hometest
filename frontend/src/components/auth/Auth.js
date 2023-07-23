import { FormControl, Input,Button, InputAdornment, InputLabel, TextField, Collapse, IconButton } from '@mui/material'
import { useForm, Controller } from 'react-hook-form';
import React, { useEffect, useState } from 'react'
import { Form } from "react-bootstrap";
import { Link, useNavigate } from 'react-router-dom';
import { BASE_URL, LOGIN } from '../../utils/ApplicationUrl';
import axios from 'axios';
import Alert from "@mui/material/Alert";
import CloseIcon from "@mui/icons-material/Close";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

function Auth() {
    let navigate= useNavigate()
    const [open, setOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [error, setError] = useState("");

    const [showPassword, setShowPassword] = useState(false);
  
    const handleClickShowPassword = () => {
      setShowPassword(!showPassword);
    };
    const handleClick = (data) => {

        console.log(data);
    // Send login data to the server using Axios
    axios.post(BASE_URL+LOGIN, data)
      .then(function (response) {
        // handle success
        console.log(response.status);
        if (response.status == 200) {
          // Store the token and user data in local storage on successful login
          localStorage.setItem("token", response.data.token);
          localStorage.setItem("user", JSON.stringify(response.data.user));
         navigate('/dashboard'); // Redirect to the dashboard page
        } else {
          setError("please try again after some time.");
          setOpen(true);
        }
      })
      .catch(function (error) {
        // handle error
        setError(error.response.data.message);
        setOpen(true);
      })
      .finally(function () {
        // always executed
      });
  };

  // Check if the user is already logged in (based on the presence of token in local storage)
  useEffect(() => {
    if (localStorage.getItem("token") != null) {
      navigate('/dashboard'); // Redirect to the dashboard page if logged in
    }
  }, []);
        
    
    const { control, handleSubmit, formState: { errors }} = useForm( );
  return (
    <div>
        <section class="pt-5 pb-5 mt-0 align-items-center " >
        <div class="container-fluid">
          <div class="row  justify-content-center align-items-center  text-center h-100">
            <div class="col-12 col-md-4 col-lg-4 col-sm-12  ">
              <div class="card shadow">
                <div class="card-body ">
                  <h4 class="card-title mt-3 text-center">Login into your Account</h4>
                  <Collapse in={open}>
          <Alert
            severity="error"
            action={
              <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                  setOpen(false);
                }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
            sx={{ mb: 2 }}
          >
            {error}
          </Alert>
        </Collapse>
                  {/* <p class="text-center">Get started with your free account</p> */}
                 
                  <Form onSubmit={handleSubmit(handleClick)}>
         <Form.Group>
         <Controller
  name="email"
  control={control}
  defaultValue=""
  rules={{
    required: { value: true, message: 'Email is required' },
    pattern: {
      value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
      message: 'Please enter a valid email address',
    },
    
    
  }}
  
  render={({ field }) => (
    <TextField
      fullWidth
      {...field}     
      label="Email"
      margin='dense'
      error={Boolean(errors?.email?.message)}
      helperText={errors?.email?.message}
      
     
    />
  )}
/>
         </Form.Group>

        
     
         <Form.Group>
       <Controller
        name="password"
        control={control}
        defaultValue=""
        rules={{
          required: { value: true, message: 'Password is required' },
          validate: {
            hasNoWhitespace: value => value.trim() !== '' || 'Password cannot contain whitespace',
          },
        }}
        render={({ field }) => (
          <TextField
            fullWidth
            {...field}
            label="Password"
            margin="dense"
            type={showPassword ? "text" : "password"}
            error={Boolean(errors?.password?.message)}
            helperText={errors?.password?.message}
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={handleClickShowPassword}
                  >
                    {showPassword ? <Visibility /> : <VisibilityOff />}
                  </IconButton>
                </InputAdornment>
              )
            }}
          />
        )}
      />
       </Form.Group>
         
         <p>Don't have an account, <Link to={'/register'}>Create your account</Link>  </p>
     
 <Button type="submit" color="primary" variant="contained" >
   Submit
 </Button>
     
       </Form>
                </div>
              </div>
            </div>
          </div>
        </div>
     </section>

    </div>
  )
}

export default Auth