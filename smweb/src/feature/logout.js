import axios from "axios";
import {persistor, store} from "../app/store";
import {soulsmatch} from "../config/soulsmatch";
import {clearTokens} from "../service/authSlice";
import {clearProfileReducer} from "../service/profileSlice";

export const logout = () => {
  axios.post(`${soulsmatch}/api/v1/auth/logout`, {}, {
    headers: {
      "Authorization": `Bearer ${store?.getState()?.authReducer?.tokens?.accessToken}`
    }
  }).then(() => {
    window.location.href = "/login"
  })
  store.dispatch(clearTokens())
  store.dispatch(clearProfileReducer())
  persistor.purge()
}