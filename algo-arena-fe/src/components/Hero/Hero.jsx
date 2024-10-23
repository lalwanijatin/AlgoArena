import { Link } from "react-router-dom";
import { useState } from "react";
import bkgImg from "../../assets/hero-wallpaper.jpg";
import Navbar from "../Header/Navbar";
import LoggingPopupLayout from "../Auth/SigninLayout";

const Hero = () => {
  const [popup, setpopup] = useState(false);

  return (
    <>
      <Navbar minWidth={'100vw'} />

      <div
        className="hero w-screen h-screen"
        style={{
          backgroundImage: `url(${bkgImg})`,
        }}
      >
        <div className="hero-content text-center text-neutral-content z-0 absolute top-16 ">
          <div className="max-w-md">
            <h1 className="mb-5 text-5xl font-bold">Hello there! ✋</h1>
            <p className="mb-5">
              AlgoArena is the best platform to help you enhance your skills,
              expand your knowledge and prepare for technical interviews.
            </p>

            <Link to="/ProblemSet">
              <button className="btn btn-primary">
                Go to Problems
              </button>
            </Link>
          </div>
        </div>
      </div>

      {popup && <LoggingPopupLayout popup={popup} setpopup={setpopup} />}
    </>
  );
};

export default Hero;
