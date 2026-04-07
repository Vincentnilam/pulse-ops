import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import type { JobStatusUpdateMessage } from "../types/job";

type OnMessage = (message: JobStatusUpdateMessage) => void;

export function connectToJobUpdates(onMessage: OnMessage) {
  const client = new Client({
    webSocketFactory: () => new SockJS("/ws"),
    reconnectDelay: 5000,
    onConnect: () => {
      console.log("Connected to job updates");

      client.subscribe("/topic/jobs", (message) => {
        const body = JSON.parse(message.body) as JobStatusUpdateMessage;
        console.log("Received:", body);
        onMessage(body);
      });
    },
    onStompError: (frame) => {
      console.error("Broker error:", frame.headers["message"]);
      console.error("Details:", frame.body);
    },
    onWebSocketError: (event) => {
      console.error("WebSocket error:", event);
    }
  });

  client.activate();

  return () => {
    client.deactivate();
  };
}