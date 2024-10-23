
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Hero from "./components/Hero/Hero";
import ProblemSet from "./components/Problems/ProblemSet";
import SigninLayout from "./components/Auth/SigninLayout";
import CodingArea from "./components/CodingArea/CodingArea";
import Submissions from "./components/Submissions/Submissions";


function App() {
  return (
        <Router>
            <Routes>
            <Route path="/" element={<Hero />} />

            <Route path="/layout/:loginOption" element={<SigninLayout />} />
            <Route path="/ProblemSet" element={<ProblemSet />} />
            <Route path="/Problems/:id/:name" element={<CodingArea />} />
            <Route path="/problems/:id" element={<CodingArea />} />
            <Route path="/submissions" element={<Submissions />} />

            </Routes>
        </Router>
  );
}

export default App;