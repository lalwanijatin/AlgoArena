import LanguageSelector from "./LanguageSelector";
import CodeMirror from "@uiw/react-codemirror";
import { bbedit } from "@uiw/codemirror-theme-bbedit";
import { javascript } from "@codemirror/lang-javascript";
import CodeEditorFooter from "./CodeEditorFooter";
import { useState } from "react";

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {  useRecoilValue, useSetRecoilState } from "recoil";
import { languageIdState } from "../../../state/Language";
import { useEffect } from "react";
import { popupState } from "../../../state/SignInPopup";
import axiosInstance from "../../../axiosInstance";

function CodeEditor({ Problem }) {
  const [inputCode, setInputCode] = useState(Problem?.initialCode);
  const[inProgress, setInprogress] = useState(false);
  const languageId = useRecoilValue(languageIdState);
  const setPopup = useSetRecoilState(popupState);

  useEffect(() => {
    setInputCode(Problem?.initialCode);
  }, [Problem]);

  const handleSubmit = async () => {
    setInprogress(true);
    axiosInstance.post("/api/submit",{
      problemName:Problem?.problem?.name,
      userCode:inputCode,
      languageId:languageId
    },{
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }).then(async (response) => {
      getSubmissionStatus(response.data.submissionId,1,setInprogress)

    }).catch((error) => {
      if(error?.response?.status === 401){
        setPopup(true);
        setInprogress(false);
        return;
      }
      alert("Something went wrong!");
      setInprogress(false);
    })
  };
  const handleOnChange = async(value) => {
    setInputCode(value)
  }

  return (

    //{/* <div className="min-w-0 mb-2 bg-white m-2 ml-0.5 rounded-md" style={{minWidth: '400px', minHeight:"90vh", display:'flex', flexDirection:'column', flexGrow:1}}> */}
    <div className="min-w-0 mb-2 bg-white m-2 ml-0.5 rounded-md" style={{minWidth: '400px', height:"85vh", display:'flex', flexDirection:'column', flexGrow:1}}>
      <LanguageSelector />
      <div className="w-full overflow-auto" style={{flexGrow:1}}>
          <CodeMirror
            value={
              inputCode
            }
            onChange={handleOnChange}
            theme={bbedit}
            extensions={[javascript()]}
            style={{ fontSize: 16 }}
          />
        </div>

        <ToastContainer />
      <CodeEditorFooter handleSubmit={handleSubmit} inProgress={inProgress}/>
      
    </div>
    
  );
}

export default CodeEditor;


function getSubmissionStatus(id,attempt,setInprogress){
  return axiosInstance.get("/api/status/"+id,{
    headers:{
      Authorization: "Bearer " + localStorage.getItem("token")
    }
  }).then((response) => {
    if(response.data.status === "ACCEPTED"){
      toast.success("Accepted!")
      setInprogress(false);
    }else if(response.data.status === "REJECTED"){
      toast.error("Rejected!")
      setInprogress(false);
    }else{
      if(attempt < 10){
        setTimeout(() => getSubmissionStatus(id,attempt + 1,setInprogress), 2000); // Retry after 2 seconds
      }else{
        setInprogress(false);
        toast.warn("Taking too long!");
      }
    }
  }).catch((error) => {
    alert("Error while fetching the status : "+JSON.stringify(error));
    setInprogress(false);
  })
}
