
import { useState } from "react";
import { useSetRecoilState } from "recoil";
import { usernameState } from "../../state/User";
import { popupState } from "../../state/SignInPopup";
import axiosInstance from "../../axiosInstance";

const LoginDiv = ({ setReg }) => {
  const [usernameLocal, setUsernameLocal] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const setUsername = useSetRecoilState(usernameState);
  const setpopup = useSetRecoilState(popupState);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const credentialsBase64 = btoa(`${usernameLocal}:${password}`);
    axiosInstance.post('/api/login', {}, {
      headers: {
        'Authorization': `Basic ${credentialsBase64}`,
        'Content-Type': 'application/json' // or 'application/x-www-form-urlencoded' based on your server's expectation
      }
    }).then(function (response) {
      localStorage.setItem('token',response.data.token);
      localStorage.setItem("username",usernameLocal);
      setUsername(usernameLocal);
      setpopup((prev) => !prev);
    }).catch(function (error) {
      console.log('Login Error Response => ', error.response);
    });
  };

  const handleSignUp = () => {
    setReg(true);
  };

  return (
    <form className="space-y-6 px-6 pb-4 " onSubmit={handleSubmit}>
      <h3 className="text-xl font-medium text-white">Log in to AlgoArena</h3>
      <div>
        <label
          htmlFor="text"
          className="text-sm font-medium block mb-2 text-gray-300"
        >
          Username
        </label>
        <input
          type="text"
          value={usernameLocal}
          onChange={(e) => setUsernameLocal(e.target.value)}
          className="border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-600 border-gray-500 placeholder-gray-400 text-white"
          placeholder="username"
          required
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
          className="border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-600 border-gray-500 placeholder-gray-400 text-white"
          placeholder="*******"
          required
        />
      </div>

      <button
        type="submit"
        className="w-full text-xl text-white border-white focus:ring-blue-300 hover:ring-blue-500 font-medium rounded-lg text-sm px-5 py-2.5 text-center hover:bg-accent-orange-s"
        disabled={loading}
      >
        {loading ? "Logging in..." : "Log in"}
      </button>

      <div className="text-sm font-medium text-gray-300">
        Don't have an account?{" "}
        <span
          className="text-blue-700 hover:underline"
          onClick={handleSignUp}
          role="button"
          tabIndex={0}
        >
          Sign up
        </span>
      </div>
    </form>
  );
};

export default LoginDiv;
