import { useState } from "react";
import {  useSetRecoilState } from "recoil";
import { usernameState } from "../../state/User";
import { popupState } from "../../state/SignInPopup";
import axiosInstance from "../../axiosInstance";

const SignupDiv = ({ setReg}) => {
  const [isLoading, setLoading] = useState(false);
  const [usernameLocal, setUsernameLocal] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const setUsername = useSetRecoilState(usernameState);
  const setpopup = useSetRecoilState(popupState);
  const [usernameAlreadyExists,setUsernameAlreadyExists] = useState(false);



  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      alert("The passwords don't match! tch tch...");
      return;
    }

    axiosInstance.post('/api/register', {
      username: usernameLocal,
      password: password,
    })
    .then(function (response) {
      localStorage.setItem("token",response.data.token);
      localStorage.setItem("username",usernameLocal);
      setUsername(usernameLocal);
      setpopup((prev) => !prev);
      alert("You have successfully registered");
    }).catch(function (error) {
      if(error?.response?.status === 400) setUsernameAlreadyExists(true)
    });
  };

  const handleSignIn = () => {
    setReg(false);
  };

  return (
    <form className="space-y-6 px-6 pb-4" onSubmit={handleSubmit}>
      <h3 className="text-xl font-medium text-white">Register to LeetClone</h3>
      <div>
        <label
          htmlFor="text"
          className="text-sm font-medium block mb-2 text-gray-300"
        >
          Username
        </label>
        {usernameAlreadyExists && <p style={{color:"red"}}>Username already exists!</p>}
        <input
          type="text"
          value={usernameLocal}
          onChange={(e) => setUsernameLocal(e.target.value)}
          className="
                border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                bg-gray-600 border-gray-500 placeholder-gray-400 text-white
            "
          placeholder="username"
        />
      </div>

      <div>
        <label
          htmlFor="password"
          className="text-sm font-medium block mb-2 text-gray-300"
        >
          Password
        </label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="
                border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                bg-gray-600 border-gray-500 placeholder-gray-400 text-white
            "
          placeholder="*******"
        />
      </div>

      <div>
        <label
          htmlFor="confirmPassword"
          className="text-sm font-medium block mb-2 text-gray-300"
        >
          Confirm Password
        </label>
        <input
          type="password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          className="
                border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
                bg-gray-600 border-gray-500 placeholder-gray-400 text-white
            "
          placeholder="*******"
        />
      </div>

      <button
        type="submit"
        className="w-full text-white focus:ring-blue-300 font-medium rounded-lg
        text-xl px-5 py-2.5 text-center bg-brand-orange hover:bg-brand-orange-s
                "
      >
        {isLoading ? "↻ Registering ..." : "Register"}
      </button>

      <div className="text-sm font-medium text-gray-300">
        Already have an account?{" "}
        <span
          className="text-blue-700 hover:underline"
          onClick={handleSignIn}
          role="button"
          tabIndex={0}
        >
          Sign in...
        </span>
      </div>
    </form>
  );
};

export default SignupDiv;
