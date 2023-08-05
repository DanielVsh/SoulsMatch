import {useGetPageableChatsQuery} from "../../service/chatApi";
import {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import jwtDecode from "jwt-decode";
import {NavLink} from "react-router-dom";

export const ChatsPage = () => {

  const token = useSelector(state => state.authReducer.tokens.accessToken)
  const username = jwtDecode(token)?.sub ?? ""

  const [chats, setChats] = useState([]);

  const {data: chatsPageableData, isLoading} = useGetPageableChatsQuery(username)

  useEffect(() => {
    chatsPageableData && setChats(chatsPageableData?.content)
  }, [chatsPageableData]);


  if (isLoading) {
    return "Loading..."
  }

  console.log(chatsPageableData)

  return (
    <>
      {chats.map((chat) => {
        const participant = chat?.participants.filter((participant) => participant.email !== username)[0];
        return (
          <NavLink to={`/chat/${chat?.id}`} replace={true}>
            {participant?.firstName} {participant?.lastName}

          </NavLink>
        );
      })}

      Hello
    </>
  )
}