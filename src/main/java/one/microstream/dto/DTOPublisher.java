package one.microstream.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record DTOPublisher(String mail, String company)
{
	
}
