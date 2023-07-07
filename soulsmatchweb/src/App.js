import './App.css';
import {MainPage} from "./component/MainPage";
import {Route, Routes} from "react-router-dom";
import {LoginPage} from "./component/LoginPage";
import {RegistrationPage} from "./component/RegistrationPage";
import {CreateProfilePage} from "./component/CreateProfilePage";
import {NavBar} from "./component/NavBar";
import {MatchPage} from "./component/MatchPage";
import {useSelector} from "react-redux";
import {AuthenticatedRoute} from "./component/utils/auth/AuthenticatedRoute";
import {PrivateRoute} from "./component/utils/auth/PrivateRoute";

function App() {

  const isAuthenticated = useSelector(state => state?.authReducer?.tokens?.accessToken);

  return (
    <>
      <div className={"h-full grid grid-rows-[15%,85%]"}>
        <div>
          {isAuthenticated && <NavBar/>}
        </div>
        <div className={"px-4"}>
          <Routes>
            <Route path={""} element={<MainPage/>}/>
            <Route element={<AuthenticatedRoute isAuthenticated={isAuthenticated}/>}>
              <Route path={"login"} element={<LoginPage/>}/>
              <Route path={"registration"} element={<RegistrationPage/>}/>
            </Route>
            <Route element={<PrivateRoute isAuthenticated={isAuthenticated}/>}>
              <Route path={"match"} element={<MatchPage/>}/>
              <Route path={"profile/create"} element={<CreateProfilePage/>}/>
            </Route>
          </Routes>
        </div>
      </div>
    </>
  );
}

export default App;
