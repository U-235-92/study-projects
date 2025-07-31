package aq.project.models;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;

//@SqlResultSetMapping(name = "users_authorities_ids_mapping", entities = 
//	@EntityResult(entityClass = UsersAuthorities.class, fields = {
//				@FieldResult(name = "userId", column = "user_id"),
//				@FieldResult(name = "authorityId", column = "authority_id")
//			}))
@Getter
@AllArgsConstructor
@SqlResultSetMapping(name = "users_authorities_table_mapping", classes = 
		@ConstructorResult(targetClass = UsersAuthorities.class, columns = {
				@ColumnResult(name = "userId", type = Integer.class),
				@ColumnResult(name = "authorityId", type = Integer.class)
		}))
public class UsersAuthorities {

	private Integer userId;
	private Integer authorityId;
}
