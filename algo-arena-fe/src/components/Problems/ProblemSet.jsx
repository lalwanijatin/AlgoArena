import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import Navbar from "../Header/Navbar";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Box, TablePagination } from '@mui/material';
import { BsCheckCircle } from "react-icons/bs";
import axiosInstance from "../../axiosInstance";

const headerStyle = {
    bgcolor: '#f5f5f5', fontWeight: 'bold', padding: '16px', fontSize: '16px'
  }

const NewProblemSet = () => {

    const[problemList, setProblemList] = useState([]);
    useEffect(() => {
      axiosInstance.get("/api/problems",{
            headers: localStorage.getItem("token") ? {Authorization: "Bearer "+localStorage.getItem("token")} : {}
        }).then((response) => {
        setProblemList(response.data);
        }).catch(error => {
          if(error?.response?.status === 401){ // Incase the auth fails because of expired token
            axiosInstance.get("/api/problems").then((response) => {
              setProblemList(response.data);
            })
          }
        })
    },[]);

    const [page, setPage] = useState(0); // State to handle the current page
    const [rowsPerPage, setRowsPerPage] = useState(5); // State to handle the number of rows per page

    // Handle page change
    const handleChangePage = (event, newPage) => {
    setPage(newPage);
    };

    // Handle rows per page change
    const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0); // Reset to the first page when changing rows per page
    };

    // Calculate rows to display
    const displayedRows = problemList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

  return (
    <>
      <Navbar minWidth={'100vw'}/>

      <Box
        display="block"
        sx={{
          width: '70%', 
          margin: '50px auto 0',
        }}
        >
            {/* Problem Set Title */}
            <div className="font-bold text-5xl font-serif" style={{ marginBottom: '10px' }}>
                Problem Set
            </div>
    
            <Box 
            display="flex" 
            justifyContent="center" 
            alignItems="center" 
            sx={{ overflowX: 'auto', width: '100%' }}  // Ensures no white space
            >           
                <TableContainer component={Paper} sx={{ display: 'inline-block', maxWidth: '100%' }}>
                <Table sx={{ minWidth: 650 }}> {/* Ensure the table has a minimum width */}
                    <TableHead>
                    <TableRow>
                        <TableCell sx={{ ...headerStyle, minWidth: 100 }}>Status</TableCell>
                        <TableCell sx={{ ...headerStyle, minWidth: 150 }}>Problem</TableCell>
                        <TableCell sx={{ ...headerStyle, minWidth: 100 }}>Acceptance</TableCell>
                        <TableCell sx={{ ...headerStyle, minWidth: 100 }}>Difficulty</TableCell>
                        <TableCell sx={{ ...headerStyle, minWidth: 100 }}>Solution</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {displayedRows.map((row) => (
                        <TableRow key={row?.name}>
                        <TableCell>
                            {row?.solvedByCurrentUser && (
                            <BsCheckCircle color="blue" fontSize={"18"} width={"18"} />
                            )}
                        </TableCell>
                        <TableCell>
                            <Link 
                            to={`/problems/${row?.id}`} 
                            className="text-blue-500 underline hover:text-blue-700 w-full"
                            >
                            {row?.name}
                            </Link>
                        </TableCell>
                        <TableCell>{row?.acceptance + "%"}</TableCell>
                        <TableCell>{row?.difficulty}</TableCell>
                        <TableCell>{"COMING SOON"}</TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>

                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={problemList.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
                </TableContainer>
            </Box>

        </Box>
    </>
  );
};

export default NewProblemSet;
