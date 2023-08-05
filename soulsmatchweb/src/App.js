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
import {ChatsPage} from "./component/chat/ChatsPage";
import WebSocketChatPage from "./component/chat/WebSocketChatPage";
import {LikedProfilePage} from "./component/LikedProfilePage";

function App() {

  const isAuthenticated = useSelector(state => state?.authReducer?.tokens?.accessToken);

  return (
    <>
      <div className="app-wrapper">
        <div className={"navbar-wrapper"}>
          {isAuthenticated
            && <NavBar/>
          }
        </div>
        <div className={"content-wrapper px-4"}>
          <Routes>
            <Route path={""} element={<MainPage/>}/>
            <Route element={<AuthenticatedRoute isAuthenticated={isAuthenticated}/>}>
              <Route path={"login"} element={<LoginPage/>}/>
              <Route path={"registration"} element={<RegistrationPage/>}/>
            </Route>
            <Route element={<PrivateRoute isAuthenticated={isAuthenticated}/>}>
              <Route path={"match"} element={<MatchPage/>}/>
              <Route path={"profile/create"} element={<CreateProfilePage/>}/>
              <Route path={"chat"} element={<ChatsPage/>}/>
              <Route path={"chat/:id"} element={<WebSocketChatPage/>}/>
              <Route path={"likes"} element={<LikedProfilePage />} />
            </Route>
          </Routes>
        </div>
      </div>
    </>
  );
}

export default App;
