package aq.project.mapper;

import aq.project.dto.ProductDto;
import aq.project.model.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-09T18:45:11+0400",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250814-1944, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl extends ProductMapper {

    @Override
    public Product toProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setColor( productDto.getColor() );
        product.setId( productDto.getId() );
        product.setName( productDto.getName() );
        product.setParty( productDto.getParty() );

        product.setCreatedAt( toLocalDateTime(productDto.getCreatedAt()) );

        return product;
    }

    @Override
    public ProductDto toProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setColor( product.getColor() );
        productDto.setId( product.getId() );
        productDto.setName( product.getName() );
        productDto.setParty( product.getParty() );

        productDto.setCreatedAt( toMills(product.getCreatedAt()) );

        return productDto;
    }
}
