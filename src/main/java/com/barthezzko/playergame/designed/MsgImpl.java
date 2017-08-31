package com.barthezzko.playergame.designed;

import com.barthezzko.playergame.interfaces.Msg;

public class MsgImpl implements Msg {

		private String payload;
		private String sender;
		private String receiver;

		private MsgImpl() {
		}

		public static Msg of(String payload, String receiver, String sender) {
			MsgImpl msg = new MsgImpl();
			msg.payload = payload;
			msg.sender = sender;
			msg.receiver = receiver;
			return msg;
		}

		public static Msg reverse(Msg msg, String augment) {
			return of(msg.getPayload() + augment, msg.getSender(), msg.getReceiver());
		}

		@Override
		public String getPayload() {
			return payload;
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
			return "MsgImpl [payload=" + payload + ", sender=" + sender + ", receiver=" + receiver + "]";
		}
	}