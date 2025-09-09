package aq.project.mapper;

import aq.project.dto.ManufactureRequestDto;
import aq.project.model.ManufactureRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-09T17:48:03+0400",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250814-1944, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ManufactureRequestMapperImpl extends ManufactureRequestMapper {

    @Override
    public ManufactureRequestDto toProduceRequestDto(ManufactureRequest request) {
        if ( request == null ) {
            return null;
        }

        ManufactureRequestDto manufactureRequestDto = new ManufactureRequestDto();

        manufactureRequestDto.setColor( request.getColor() );
        manufactureRequestDto.setCount( request.getCount() );
        manufactureRequestDto.setId( request.getId() );
        manufactureRequestDto.setProduct( request.getProduct() );

        manufactureRequestDto.setCreatedAt( toMills(request.getCreatedAt()) );

        return manufactureRequestDto;
    }
}
