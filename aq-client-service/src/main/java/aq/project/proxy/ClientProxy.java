package aq.project.proxy;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;

public interface ClientProxy {

	MessageResponse readMessage(int id);
	String deleteMessage(int id);
	String editMessage(int id, String text);
	String writeMessage(MessageRequest messageRequest);
}
