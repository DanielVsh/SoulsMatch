import {createSlice} from "@reduxjs/toolkit";

const initialState = {
  tokens: {
    accessToken: null,
    refreshToken: null
  },
}


const authSlice = createSlice({
  name: 'authReducer',
  initialState: initialState,
  reducers: {
    setTokens: (state, action) => {
      if (action.payload) {
        state.tokens = action.payload
      }
    },
    clearTokens: (state) => {
      state.tokens.accessToken = null
      state.tokens.refreshToken = null
    },
  }
})

export const {
  setTokens,
  clearTokens
} = authSlice.actions;

export default authSlice.reducer;