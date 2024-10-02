package one.microstream.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record DTOAuthor(String mail, String firstname, String lastname)
{
	
}
