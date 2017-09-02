package com.barthezzko.playergame.impl;

import com.barthezzko.playergame.model.Message;

public final class MessageImpl implements Message {

	private StringBuilder payload;
	private String sender;
	private String receiver;

	private MessageImpl(String message, String receiver, String sender) {
		this.payload = new StringBuilder(message);
		this.sender = sender;
		this.receiver = receiver;
	}

	public Message reverseAndAppend(String augment) {
		return new MessageImpl(payload.toString() + augment, sender, receiver);
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

	public static class Builder {
		private String payload;
		private String sender;
		private String receiver;

		public Builder payload(String payload) {
			this.payload = payload;
			return this;
		}

		public Builder sender(String sender) {
			this.sender = sender;
			return this;
		}

		public Builder receiver(String receiver) {
			this.receiver = receiver;
			return this;
		}

		public Message build() {
			return new MessageImpl(payload, receiver, sender);
		}
	}
}