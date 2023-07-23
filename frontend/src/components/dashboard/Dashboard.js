import React, { useEffect, useMemo, useState } from 'react'
import SideNav from '../sideNav/SideNav'
import '../../App.css'
import axios from 'axios';
import { BASE_URL, GET_LOGS } from '../../utils/ApplicationUrl';
import { MaterialReactTable } from 'material-react-table';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
    let navigate= useNavigate()
    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [data, setData] = useState([]);
    const [name, setName] = useState("");
    const token = localStorage.getItem("token");
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    useEffect(() => {
        if(typeof window !== 'undefined') {
          // localStorage access here
          if (localStorage.getItem("token") == null) {
            navigate("/");
          }
        }
      
      }, []);
    
      // Fetch the authentication logs from the server on component mount
      useEffect(() => {
        axios
          .get(BASE_URL + GET_LOGS)
          .then(function (response) {
            // handle success
            console.log(response.data);
            if (response.status == 200) {
              setData(response.data);
              setName(JSON.parse(localStorage.getItem("user")).name);
            } else {
              setError("Something went wrong, please try again after some time.");
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
      }, []);
    const columns = useMemo(
        () => [
          {
            accessorKey: 'email',
            header: 'Email',
            
          },
          {
            accessorKey: 'currentDateAndTime',
            header: 'Date and Time',
            // accessorFn: (row) => <Moment>{row}</Moment>,
           
          },
          {
            accessorKey: 'action',
            header: 'Action',
           
          },
        ],
        [],
      );
  return (
    <div>
        <SideNav/>
        <div className='main-content'>

            <h1>Welcome {name} </h1>

            <MaterialReactTable columns={columns} data={data} />
            

            </div>
    </div>
  )
}

export default Dashboard