
import SigninDiv from "./SigninDiv";
import SignupDiv from "./SignupDiv";
import { useState } from "react";
import { IoClose } from "react-icons/io5";
import { useRecoilState } from "recoil";
import { popupState } from "../../state/SignInPopup";

const LoggingPopupLayout = () => {
  const [reg, setReg] = useState(true);
  const[popup, setpopup] = useRecoilState(popupState)

  return (
    <div className="fixed top-0 left-0 h-screen w-screen flex items-center justify-center z-30 ">
      <div className="bg-black  opacity-50 top-0 left-0 w-full h-full absolute z-40"></div>
      <div className="bg-opacity-50  bg-white backdrop-blur-lg w-full max-w-md  z-50 rounded-lg overflow-hidden">
        <div className="p-6 text-[#333] relative">
          <div className="cursor-pointer absolute top-2 right-2">
            <IoClose
              fontSize={45}
              color="white"
              onClick={() => setpopup(!popup)}
            />
          </div>
          <div>
            {reg ? (
              <SignupDiv setReg={setReg} />
            ) : (
              <SigninDiv setReg={setReg} />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoggingPopupLayout;
