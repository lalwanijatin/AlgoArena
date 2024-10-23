import Navbar from "../Header/Navbar";
import Split from "react-split";
import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { languageIdState } from "../../state/Language";
import Description from "./Description/Description";
import CodeEditor from "./CodeEditor/CodeEditor";
import axiosInstance from "../../axiosInstance";

function CodingArea() {
  const data = useLocation().pathname.split("/");
  const problemId = data[data.length - 1];
  const[Problem, setProblem] = useState(null);
  const[inProgress, setInprogress] = useState(true);
  const languageId = useRecoilValue(languageIdState);


  useEffect(() => {
    axiosInstance.get("/api/problems/"+problemId+"/"+languageId).then((response) => {
      console.log("response :: "+JSON.stringify(response));
      setProblem(response.data);
      setInprogress(false);
    })
  },[languageId]);

  return (
    <>
      <Navbar minWidth={'800px'}></Navbar>

      {inProgress && <span className="loading loading-spinner loading-lg"></span>}

      {!inProgress && 
      <Split className="split flex h-full" >
        <Description Problem={Problem} />
        <CodeEditor Problem={Problem} />
      </Split>}
    </>
  );
}

export default CodingArea;
