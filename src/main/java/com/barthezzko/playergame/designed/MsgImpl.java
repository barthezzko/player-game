package com.barthezzko.playergame.designed;

import com.barthezzko.playergame.interfaces.Msg;

public class MsgImpl implements Msg {

		private StringBuilder payload; 
		private String sender;
		private String receiver;

		public MsgImpl(String message, String receiver, String sender) {
			this.payload = new StringBuilder(message);
			this.sender = sender;
			this.receiver = receiver;
		}

		public void reverseAndAppend(String augment) {
			payload.append(augment);
			String temp = sender;
			sender = receiver;
			receiver = temp;
		}

		@Override
		public String getPayload() {
			return payload.toString();
		}

		@Override
		public String getSender() {
			return sender;
		}

		@Override
		public String getReceiver() {
			return receiver;
		}

		@Override
		public String toString() {
			return "MsgImpl [payload=" + payload.toString() + ", sender=" + sender + ", receiver=" + receiver + "]";
		}
	}