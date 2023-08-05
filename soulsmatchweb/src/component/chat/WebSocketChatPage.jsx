import React, {useEffect, useRef, useState} from "react";
import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {useSelector} from "react-redux";
import jwtDecode from "jwt-decode";
import {useParams} from "react-router-dom";
import {useGetPageableMessagesByChatIdQuery} from "../../service/chatApi";

const WebSocketChatPage = () => {
  const [text, setText] = useState("");
  const [page, setPage] = useState(0);

  const {id: chatId} = useParams()

  const ref = useRef(false);
  const containerRef = useRef(null);

  const token = useSelector((state) => state.authReducer.tokens.accessToken);
  const username = jwtDecode(token)?.sub;

  const [stompClient, setStompClient] = useState(null);

  const [messages, setMessages] = useState([])

  const {data: pageableMessagesData, isLoading: isLoadingMessages}
    = useGetPageableMessagesByChatIdQuery({
    chatId,
    pageable: {
      sort: {
        element: "time",
        direction: "asc"
      },
      page: page,
      size: 20
    }
  })


  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client();
    client.webSocketFactory = () => socket;
    client.onConnect = () => {
      setStompClient(client);
      client.subscribe(`/topic/chat/${chatId}/messages`, (messageOutput) => {
        setMessages((prevResponse) => [...prevResponse, JSON.parse(messageOutput.body)]);
      });
    };

    client.activate();

    return () => {
      if (client !== null) {
        client.deactivate();
      }
    };
  }, []);


  useEffect(() => {
    if (!ref.current) {
      ref.current = true;
    } else {
      if (pageableMessagesData) {
        const newMessages = pageableMessagesData.content.filter(
          (message) => !messages.some((msg) => msg.id === message.id)
        );
        setMessages((prevState) => [...newMessages, ...prevState,]);
      }
    }
  }, [pageableMessagesData]);

  const sendMessage = (event) => {
    event.preventDefault()
    if (text.trim() !== "") {
      const message = {
        chat: chatId,
        username: username,
        content: text,
        isRead: false,
      };
      stompClient.publish({
        destination: `/app/chat/${chatId}/message`,
        body: JSON.stringify(message),
      });
      setText("");
    }
  };

  if (isLoadingMessages) {
    return "Loading..."
  }

  console.log(page)
  return (
    <div>
      <div>
        <form>
          <div ref={containerRef} style={{overflowY: "auto", maxHeight: "400px"}}>
            {messages.map((message, index) => (
              <p key={index}>
                {message.soul?.firstName}: {message?.content} ({message?.time})
              </p>
            ))}
          </div>
          <input
            type="text"
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder="Write a message..."
          />
          <button onClick={sendMessage}>Send</button>
        </form>
      </div>
    </div>
  );
};

export default WebSocketChatPage;
