import {createSlice} from "@reduxjs/toolkit";

const initialState = {
  match: {
    profileNumber: 0
  }
}


const profileSlice = createSlice({
  name: 'profileReducer',
  initialState: initialState,
  reducers: {
    setProfileNumber: (state, action) => {
      if (action.payload) {
        state.match.profileNumber = action.payload
      }
    },
    clearProfileReducer: (state) => {
      state.match.profileNumber = 0
    },
  }
})

export const {
  setProfileNumber,
  clearProfileReducer
} = profileSlice.actions;

export default profileSlice.reducer;