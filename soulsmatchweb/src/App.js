import './App.css';
import {MainPage} from "./component/MainPage";
import {Route, Routes} from "react-router-dom";
import {LoginPage} from "./component/LoginPage";
import {RegistrationPage} from "./component/RegistrationPage";
import {CreateProfilePage} from "./component/CreateProfilePage";
import {NavBar} from "./component/NavBar";
import {MatchPage} from "./component/MatchPage";
import {getAuthenticatedUser} from "./feature/getAuthenticatedUser";

function App() {
  return (
    <>
      {getAuthenticatedUser() !== null && (
        <NavBar/>
      )}
      <div className={"app-wrapper"}>
        <Routes>
          <Route path={""} element={<MainPage/>}/>
          <Route path={"login"} element={<LoginPage/>}/>
          <Route path={"registration"} element={<RegistrationPage/>}/>
          <Route path={"match"} element={<MatchPage/>}/>
          <Route path={"profile/create"} element={<CreateProfilePage/>}/>
        </Routes>
      </div>
    </>
  );
}

export default App;
