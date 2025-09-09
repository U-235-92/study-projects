package aq.project.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

import aq.project.dto.ProductDto;
import aq.project.model.Product;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class ProductMapper {

	@Mapping(target = "createdAt", expression = "java(toLocalDateTime(productDto.getCreatedAt()))")
	public abstract Product toProduct(ProductDto productDto);
	
	@Named("toLocalDateTime")
	protected LocalDateTime toLocalDateTime(long createdAt) {
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.systemDefault());
		return ldt;
	}
	
	@Mapping(target = "createdAt", expression = "java(toMills(product.getCreatedAt()))")
	public abstract ProductDto toProductDto(Product product);
	
	@Named("toMills")
	protected long toMills(LocalDateTime createdAt) {
		long mills = createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return mills;
	}
}
