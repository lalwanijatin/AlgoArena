import { Link } from "react-router-dom";
import LoggingPopupLayout from "../Auth/SigninLayout";
import { useRecoilState, useRecoilValue } from "recoil";
import { usernameState } from "../../state/User";
import { popupState } from "../../state/SignInPopup";
import { Box, Modal, Typography } from "@mui/material";
import { useState } from "react";
import MuiLink from '@mui/material/Link';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 3, // Reduced padding for a slightly more compact look
    borderRadius: '8px',
  };

function Navbar({minWidth}){

    const [popup, setpopup] = useRecoilState(popupState)

    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const username = useRecoilValue(usernameState);
    
    const handleLogout = async () => {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        window.location.reload();
    };


    return <>
        <div className="navbar bg-accent" style={{width: '100vw', minWidth: minWidth}}>
            <div className="navbar-start">
                <div className="dropdown">
                <div tabIndex={0} role="button" className="btn btn-ghost lg:hidden">
                    <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="h-5 w-5"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor">
                    <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M4 6h16M4 12h8m-8 6h16" />
                    </svg>
                </div>
                <ul
                    tabIndex={0}
                    className="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow">
                    <li><Link
                                to={"/ProblemSet"}
                                className="flex items-center gap-2 font-medium max-w-[170px] cursor-pointer"
                                >
                                <p>Problem List</p>
                            </Link>
                        
                    </li>
                  
                    {localStorage.getItem("token") && ( <li><Link
                                to={"/Submissions"}
                                className="flex items-center gap-2 font-medium max-w-[170px] cursor-pointer"
                            >
                                <p>Submissions</p>
                            </Link>
                    </li>)}

                    <li><p onClick={handleOpen} className="flex items-center gap-2 font-medium cursor-pointer">Submit A Problem</p></li>

                </ul>
                </div>
                <Link to={"/"} > <a className="btn btn-ghost text-xl">AlgoArena</a> </Link>
            </div>
            <div className="navbar-center hidden lg:flex">
                <ul className="menu menu-horizontal px-1">
                    <li>    <Link
                                to={"/ProblemSet"}
                                className="flex items-center gap-2 font-medium max-w-[170px] cursor-pointer"
                                >
                                <p>Problem List</p>
                            </Link>
                        
                    </li>


                    {localStorage.getItem("token") && ( <li><Link
                                to={"/Submissions"}
                                className="flex items-center gap-2 font-medium max-w-[170px] cursor-pointer"
                            >
                                <p>Submissions</p>
                            </Link>
                    </li>)}

                    <li><p onClick={handleOpen} className="flex items-center gap-2 font-medium cursor-pointer">Submit A Problem</p></li>

                </ul>
            </div>

            {/* Sign up button OR Avatar*/}
            <div className="navbar-end">
                <a className="btn">
                    {!username && (
                        <div className="flex align-middle items-center justify-center w-fit">
                            <button
                            className="bg-secondary border-4 border-white text-white p-1.5 px-3 rounded font-semibold text-sm hover:bg-white border-blue-800 hover:text-blue-800 transition ease-in-out duration-300"
                            onClick={() => setpopup(true)}
                            >
                            Sign Up
                            </button>
                        </div>
                        )}
                        {/* if logged in */}
                        {username && (
                        <div className="dropdown flex flex-col items-center gap-4 ">
                            <div
                            tabIndex={0}
                            role="button"
                            className="btn btn-ghost btn-circle avatar"
                            >
                            <div className="avatar placeholder">
                                <div className="bg-neutral text-neutral-content w-8 rounded-full">
                                <span className="text-xs">{username.charAt(0).toUpperCase()}</span>
                                </div>
                            </div>
                            </div>
                            
                            <ul
                            tabIndex={0}
                            className="menu menu-sm dropdown-content z-[1] p-2 shadow bg-base-100 rounded-2xl w-fit relative top-12 right-3 gap-2"
                            >
                            <li className="bg-accent/55 rounded-2xl ">
                                <a className="justify-between overflow-hidden">
                                {username}
                                </a>
                            </li>
                          
                            <li className="bg-accent/55 rounded-2xl ">
                                <button
                                className="bg-red-600 hover:bg-rose-200 hover:text-red-800 text-white p-2"
                                onClick={() => {
                                    handleLogout();
                                }}
                                >
                                Logout
                                </button>
                            </li>
                            </ul>
                        </div>
                    )}
                </a>
            </div>

            {popup && <LoggingPopupLayout/>}
        </div>

        <Modal
    open={open}
    onClose={handleClose}
    aria-labelledby="modal-modal-title"
    aria-describedby="modal-modal-description"
  >
    <Box sx={style}>
      <Typography variant="h5" sx={{ mb: 2 }}> {/* Added margin-bottom for spacing */}
        Submit A Problem
      </Typography>
      <Typography id="modal-modal-description" sx={{ mb: 2 }}>
        Make a Pull Request to:
      </Typography>
      <MuiLink
        href="https://github.com/lalwanijatin/AlgoArena.git"
        sx={{
          display: 'block', // Makes link take full width
          overflow: 'hidden', // Hides overflow
          textOverflow: 'ellipsis', // Adds ellipsis if text overflows
          whiteSpace: 'nowrap', // Prevents text from wrapping
          maxWidth: '100%', // Makes sure it does not exceed modal width
          textDecoration: 'none', // Optional: Remove underline for a cleaner look
          color: 'primary.main', // Use theme color for links
          '&:hover': {
            textDecoration: 'underline',
          },
        }}
        target="_blank"
      >
        https://github.com/lalwanijatin/AlgoArena.git
      </MuiLink>
    </Box>
  </Modal>
    </>
}


export default Navbar;