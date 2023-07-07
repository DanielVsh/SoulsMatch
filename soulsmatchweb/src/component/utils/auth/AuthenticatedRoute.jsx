import {Navigate, Outlet} from "react-router-dom";

export const AuthenticatedRoute = ({isAuthenticated}) => {
  return isAuthenticated
    ? <Navigate to="/" replace={true}/>
    : <Outlet/>
}