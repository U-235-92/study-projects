package aq.project.aspects;

import aq.project.exceptions.NullDtoException;

final class NullDtoChecker {

	protected static <T> void checkNullDto(T dto) throws NullDtoException {
		if(dto == null)
			throw new NullDtoException();
	}
}
