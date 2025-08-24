package aq.project.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;
import aq.project.entity.Message;
import aq.project.exception.DeleteMessageViolationException;
import aq.project.exception.EditMessageViolationException;
import aq.project.exception.MessageNotFoundException;
import aq.project.exception.UnknownAuthorException;
import aq.project.mapper.MessageMapper;
import aq.project.repository.MessageRepository;
import aq.project.security.ResourceServiceAuthenticationToken;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MessageService {

	private final MessageMapper messageMapper;
	private final MessageRepository messageRepository;
	
	public void writeMessage(@NotNull @Valid MessageRequest messageRequest) {
		Message message = messageMapper.toMessage(messageRequest);
		ResourceServiceAuthenticationToken authentication = getAuthentication();
		String authorInMessage = message.getAuthor();
		String authorInContext = authentication.getAuthor();
		if(isSameAuthor(authorInMessage, authorInContext)) {
			long postedAt = System.currentTimeMillis();
			message.setPostedAt(postedAt);
			message.setUpdatedAt(postedAt);
			messageRepository.save(message);
		} else {
			throw new UnknownAuthorException(authorInMessage);
		}
	}
	
	public MessageResponse readMessage(@Positive int id) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
		MessageResponse messageResponse = messageMapper.toMessageResponse(message);
		return messageResponse;
	}
	
	public void editMessage(@Positive int id, @NotNull String text) {
		ResourceServiceAuthenticationToken authentication = getAuthentication();
		Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
		String authorInMessage = message.getAuthor();
		String authorInContext = authentication.getAuthor();
		if(isSameAuthor(authorInMessage, authorInContext)) {			
			message.setText(text);
			message.setUpdatedAt(System.currentTimeMillis());
			messageRepository.save(message);
		} else {
			throw new EditMessageViolationException(authorInContext, authorInMessage);
		}
	}
	
	public void deleteMessage(@Positive int id) {
		ResourceServiceAuthenticationToken authentication = getAuthentication();
		Message message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
		String authorInMessage = message.getAuthor();
		String authorInContext = authentication.getAuthor();
		if(isSameAuthor(authorInMessage, authorInContext)) {				
			messageRepository.deleteById(id);
		} else {
			throw new DeleteMessageViolationException(authorInContext, authorInMessage);
		}
	}
	
	private ResourceServiceAuthenticationToken getAuthentication() {
		return (ResourceServiceAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
	}
	
	private boolean isSameAuthor(String authorInMessage, String authorInContext) {
		return authorInMessage.equals(authorInContext);
	}
}
