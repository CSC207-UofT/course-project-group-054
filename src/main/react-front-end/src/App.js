import React from "react";
import "./App.css";
import NewUser from "./pages/Register";

var globalState = {
  currentUser: null,
};

const globalStateContext = React.createContext(globalState);

function App() {
  // const [currentUser, setCurrentUser] = useState(null);
  alert(globalState.currentUser);

  return (
    <div className="App">
      <NewUser></NewUser>
    </div>
  );
}

export default App;
