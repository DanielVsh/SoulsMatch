import {combineReducers, configureStore} from '@reduxjs/toolkit'
import {profileApi} from "../service/profileApi";
import {setupListeners} from "@reduxjs/toolkit/query";
import authReducer from "../service/authSlice";
import profileReducer from "../service/profileSlice";
import storage from 'redux-persist/lib/storage';
import {persistReducer, persistStore} from 'redux-persist';
import thunk from 'redux-thunk';
import {chatApi} from "../service/chatApi";

const persistConfig = {
  key: 'root',
  storage,
}

const rootReducer = combineReducers({
  [profileApi.reducerPath]: profileApi.reducer,
  [chatApi.reducerPath]: chatApi.reducer,
  authReducer,
  profileReducer,
})

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store = configureStore({
  reducer: persistedReducer,
  middleware: [thunk].concat(
    profileApi.middleware,
    chatApi.middleware,
  )
})

setupListeners(store.dispatch)

export const persistor = persistStore(store)