
import App from './App.jsx'
import './index.css'
import { RecoilRoot } from 'recoil'
import { createRoot } from 'react-dom/client'

  // Find the root element in the HTML
const container = document.getElementById('root');

// Create a root and render the app
const root = createRoot(container);
root.render(
  <RecoilRoot>
    <App />
  </RecoilRoot>
);