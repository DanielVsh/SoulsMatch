import {store} from "../app/store";

export const getAuthenticatedUser = () => {
  const username = store?.getState()?.authReducer?.tokens?.accessToken

  return username;
}