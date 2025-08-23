package aq.project.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;
import aq.project.entity.Message;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class MessageMapper {

	public abstract Message toMessage(MessageRequest messageRequest);
	
	@Mapping(target = "postedAt", expression = "java(millsToFormattedString(message.getPostedAt()))")
	@Mapping(target = "updatedAt", expression = "java(millsToFormattedString(message.getUpdatedAt()))")
	public abstract MessageResponse toMessageResponse(Message message);
	
	@Named("millsToFormattedString")
	protected String millsToFormattedString(long mills) {
		Instant instant = Instant.ofEpochMilli(mills);
		LocalDateTime ldt = LocalDateTime.from(instant);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
		return ldt.format(dtf);
	}
}
