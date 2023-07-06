import axios from "axios";
import {Mutex} from "async-mutex";
import {soulsmatch} from "../config/soulsmatch";
import {store} from "../app/store";
import {logout} from "./logout";
import {fetchBaseQuery} from "@reduxjs/toolkit/dist/query/react";

const baseQuery = fetchBaseQuery({
  baseUrl: `${soulsmatch}/api/v1`,
  prepareHeaders: (headers, {getState}) => {
    const accessToken = getState()?.authReducer?.tokens?.accessToken
    if (accessToken) {
      headers.set('Authorization', `Bearer ${accessToken}`)
    }
    return headers
  }
})

const mutex = new Mutex()

export const baseQueryWithReauth = async (args, api, extraOptions) => {
  await mutex.waitForUnlock()
  let result = await baseQuery(args, api, extraOptions)
  if (result.error && result.error.status === 401) {
    if (!mutex.isLocked()) {
      const release = await mutex.acquire()
      try {
        await axios.post(`${soulsmatch}/api/v1/auth/refresh-token`, {},{
          headers: {
            "Authorization": `Bearer ${store?.getState()?.authReducer?.tokens?.refreshToken}`
          }
        }).then((response) => {
          store.getState().authReducer.tokens = response?.data
          result = baseQuery(args, api, extraOptions)
        }).catch(reason => {
          logout()
          window.location.href = "/login";
          alert("Your token has expired");
        })
      } finally {
        release()
      }
    } else {
      await mutex.waitForUnlock()
      result = await baseQuery(args, api, extraOptions)
    }
  }
  return result
}