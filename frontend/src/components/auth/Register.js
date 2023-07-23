import { FormControl, Input,Button, InputAdornment, InputLabel, TextField,IconButton, Collapse } from '@mui/material'
import { useForm, Controller } from 'react-hook-form';
import React, { useEffect, useState } from 'react'
import { Form } from "react-bootstrap";
import { Link, useNavigate } from 'react-router-dom';
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import { BASE_URL, REGISTER } from '../../utils/ApplicationUrl';
import axios from 'axios';
import Alert from "@mui/material/Alert";
import CloseIcon from "@mui/icons-material/Close";


function Register() {
    let navigate= useNavigate()
    const [open, setOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [error, setError] = useState("");
    const handleClick = (data) => {
    
        const postData = {
          email: data.email,
          password: data.password,
          name:data.name
        };
        console.log(data);
      
        axios.post(`${BASE_URL}${REGISTER}`, postData)
          .then(response => {
            console.log(response.data.data);
            if (response.status === 200) {
             
              navigate("/")
            }
            else {
                setError(" please try again after some time.");
                setOpen(true);
              }
          })
          .catch(error => {
            console.log(error.response); // Log the error response object
            if (error.response) {
              // Request was made and server responded with a status code
              console.log("error.response.status" + error.response.status);
              console.log("fhgfhgf" + error.response.data.message);
      
              if (error.response.status !== 200) {
                setError(error.response.data.message);
        setOpen(true);
              }
            } else if (error.request) {
              
              console.log(error.request);
            } else {
            
              console.log('Error', error.message);
            }
            
          });
        
    };
    useEffect(() => {
        if (localStorage.getItem("token") != null) {
          navigate("/dashboard"); // Redirect to the dashboard page if logged in
        }
      }, []);
  const { control, handleSubmit, formState: { errors }} = useForm( );
  const [showPassword, setShowPassword] = useState(false);

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };
return (
  <div>
      <section class="pt-5 pb-5 mt-0 align-items-center " >
      <div class="container-fluid">
        <div class="row  justify-content-center align-items-center  text-center h-100">
          <div class="col-12 col-md-4 col-lg-4 col-sm-12  ">
            <div class="card shadow">
              <div class="card-body ">
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
                <h4 class="card-title mt-3 text-center">Create your Account</h4>
                
               
                <Form onSubmit={handleSubmit(handleClick)}>

                <Form.Group>
         <Controller
  name="name"
  control={control}
  defaultValue=""
  rules={{
    required: { value: true, message: 'Name is required' },
    pattern: {
      value: /^[a-zA-Z]+(?:\s+[a-zA-Z]+)*$/,
      message: 'Name should contain only alphabetic characters',
    },
    minLength: { value: 3, message: 'Name should be at least 3 characters long' },
    maxLength: { value: 50, message: 'Name should not exceed 50 characters' },
   
  }}
 
  render={({ field }) => (
    <TextField
      fullWidth
      {...field}     
      label="Name"
      margin='dense'
      error={Boolean(errors?.name?.message)}
      helperText={errors?.name?.message}
     
    />
  )}
/>
         </Form.Group>
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
       
       <p>Already have an account, <Link to={'/'}>Login into your account</Link>  </p>
   
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

export default Register