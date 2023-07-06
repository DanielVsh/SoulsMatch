import {createApi} from '@reduxjs/toolkit/query/react'
import {baseQueryWithReauth} from "../feature/baseQueryWithReauth";

export const profileApi = createApi({
  reducerPath: 'profileApi',
  baseQuery: baseQueryWithReauth,
  endpoints: (builder) => ({
    getNextProfile: builder.query({
      query: () => `/profiles/next`
    }),
    getProfile: builder.query({
      query: () => `/profiles`
    }),
    dislikeProfile: builder.mutation({
      query: arg => ({
        url: `/profiles/${arg}/dislike`,
        method: "PATCH"
      })
    }),
    likeProfile: builder.mutation({
      query: arg => ({
        url: `/profiles/${arg}/like`,
        method: "PATCH"
      })
    }),
    createProfile: builder.mutation({
      query: (arg) => ({
        url: `/profiles`,
        method: "POST",
        body: arg
      }),
    }),
  }),
})

export const {
  useGetProfileQuery,
  useGetNextProfileQuery,
  useDislikeProfileMutation,
  useLikeProfileMutation,
  useCreateProfileMutation,
} = profileApi