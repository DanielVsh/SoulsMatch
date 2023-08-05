import {createApi} from "@reduxjs/toolkit/dist/query/react";
import {baseQueryWithReauth} from "../feature/baseQueryWithReauth";

export const chatApi = createApi({
  reducerPath: 'chatApi',
  baseQuery: baseQueryWithReauth,
  endpoints: (builder) => ({
    getPageableChats: builder.query({
      query: (arg) => `/chats/${arg}`
    }),
    getPageableMessagesByChatId: builder.query({
      query: (arg) => `/messages/chat/${arg.chatId}?sort=${arg.pageable.sort.element},${arg.pageable.sort.direction}&size=${arg.pageable.size}&page=${arg.pageable.page}`
    }),
    createChat: builder.mutation({
      query: (arg) => ({
        url: `/chats/create`,
        method: `POST`,
        body: arg
      })
    }),
  }),
})

export const {
  useGetPageableChatsQuery,
  useGetPageableMessagesByChatIdQuery,
  useCreateChatMutation,
} = chatApi