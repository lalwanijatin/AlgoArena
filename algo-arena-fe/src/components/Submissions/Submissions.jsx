
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Box, Button, Modal, TablePagination, Typography } from '@mui/material';
import { useState } from 'react';
import { useEffect } from 'react';
import Navbar from '../Header/Navbar';
import { Link } from 'react-router-dom';
import axiosInstance from '../../axiosInstance';


// Convert to IST (Indian Standard Time)
const options = { timeZone: 'Asia/Kolkata', hour12: false, year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };

// Style for Modal that contains code
const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '90%', // Limit width to 90% of the screen
    maxWidth: '900px', // Optionally set a max width
    maxHeight: '90%', // Limit height to 90% of the screen
    overflowY: 'auto', // Make content scrollable on the y-axis
    bgcolor: 'background.paper',
    borderRadius: '8px', // Slight rounding for a cleaner look
    border: 'none', // Remove the hard border
    boxShadow: 24,
    padding: '20px',
};


  const headerStyle = {
    bgcolor: '#f5f5f5', fontWeight: 'bold', padding: '16px', fontSize: '16px'
  }

  // Escape HTML like content in the code
  const escapeHtml = (unsafe) => {
    return unsafe
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;")
      .replace(/'/g, "&#039;");
  };

function Submissions() {

    const[submissions, setSubmissions] = useState([]);

    // Modal methods
    const [open, setOpen] = useState(false);
    const handleOpen = (selectedCode) => {
        setSelectedCode(selectedCode)
        setOpen(true);
    }
    const handleClose = () => setOpen(false);

    // Selected code for Modal
    const[selectedCode, setSelectedCode] = useState('');


    // Fetch submissions
    useEffect(() => {
        const token = localStorage.getItem("token");
        const username = localStorage.getItem("username");
        axiosInstance.get("/api/submissions/"+username,{
            headers:{
                Authorization: "Bearer "+token
            }
        }).then(response => {
            setSubmissions(response.data);
        });
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
      const displayedRows = submissions.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

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
                Submissions
            </div>
    
            <Box 
            display="flex" 
            justifyContent="center" 
            alignItems="center" 
            sx={{ overflowX: 'auto', width: '100%' }}  // Ensures no white space
            >
                <TableContainer component={Paper} sx={{ display: 'inline-block', maxWidth: '100%' }}>
                    <Table sx={{ minWidth: 650 }}>
                    <TableHead>
                        <TableRow>
                        <TableCell sx={{...headerStyle, minWidth: 100}}>Problem</TableCell>
                        <TableCell sx={{...headerStyle, minWidth: 100}}>Status</TableCell>
                        <TableCell sx={{...headerStyle, minWidth: 100}}>Submission Date</TableCell>
                        <TableCell sx={{...headerStyle, minWidth: 100}}>Code</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                        <TableRow key={row.submissionDate}>
                            <TableCell> <Link to={`/problems/${row?.problemId}`} className="text-blue-500 underline hover:text-blue-700 w-full" >{row?.problemName}</Link> </TableCell>
                            <TableCell>{row?.status}</TableCell>
                            <TableCell>{new Intl.DateTimeFormat('en-GB', options).format(new Date(row?.submissionDate))}</TableCell>
                            <TableCell><Button onClick={() => handleOpen(row?.code)}>Click To View</Button></TableCell>
                        </TableRow>
                        ))}
                    </TableBody>
                    </Table>

                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={submissions.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                    />

                </TableContainer>

                <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
                >
                    <Box sx={modalStyle}>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                        Code
                        </Typography>
                        <Box
                        id="modal-modal-description"
                        sx={{
                            mt: 2,
                            whiteSpace: 'pre-wrap', // Preserve white spaces and break lines
                            wordBreak: 'break-word', // Break long words/lines
                            fontFamily: 'monospace', // Use a monospace font for code
                            maxHeight: '80vh', // Limit the code block height within the modal
                            overflowY: 'auto', // Allow vertical scroll if the content exceeds height
                            overflowX: 'hidden', // Ensure no horizontal overflow
                            padding: '10px', // Add some padding for spacing
                            backgroundColor: '#f5f5f5', // Light background to differentiate code area
                            borderRadius: '5px', // Slight rounding for the code box
                            border: '1px solid #ccc', // Add a subtle border around the code area
                        }}
                        >
                        <span
                            dangerouslySetInnerHTML={{
                            __html: escapeHtml(selectedCode)
                                .replace(/ /g, '&nbsp;') // Replace all spaces with non-breaking spaces
                                .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;') // Replace tabs with 4 spaces
                                .replace(/\n/g, '<br/>'), // Replace newlines with <br/>
                            }}
                        />
                        </Box>
                    </Box>
                </Modal>

            </Box>
        </Box>
    </>
  );
}

export default Submissions;