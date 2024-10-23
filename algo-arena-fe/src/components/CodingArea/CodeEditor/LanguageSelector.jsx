import { MenuItem, Select } from "@mui/material";
import { useRecoilState } from "recoil";
import { languageIdState } from "../../../state/Language";

const options = {
  "java":62,
  "javascript":63
}

function LanguageSelector() {

  const [languageId,setLanguageId] = useRecoilState(languageIdState);

  const handleSelect = (event) => {
    setLanguageId(event.target.value);
    localStorage.setItem("languageId",event.target.value);
  };

  return (
  
    <div className="dropdown-container">
      <Select
        value={languageId}
        onChange={handleSelect}
        style={{ fontSize: '14px' }}
      >
        {Object.keys(options).map((key) => (
          <MenuItem key={options[key]} value={options[key]}>
            {key}
          </MenuItem>
        ))}
      </Select>
    </div>
    
  );
}

export default LanguageSelector;
