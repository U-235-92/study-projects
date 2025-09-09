package aq.project.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import aq.project.dto.ManufactureRequestDto;
import aq.project.model.ManufactureRequest;

@Component
@Mapper(componentModel = ComponentModel.SPRING)
public abstract class ManufactureRequestMapper {

	@Mapping(target = "createdAt", expression = "java(toMills(request.getCreatedAt()))")
	public abstract ManufactureRequestDto toProduceRequestDto(ManufactureRequest request);
	
	@Named("toMills")
	protected long toMills(LocalDateTime createdAt) {
		long mills = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return mills;
	}
}
