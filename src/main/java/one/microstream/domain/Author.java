package one.microstream.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Author
{
	private final String	UUID	= java.util.UUID.randomUUID().toString();
	private String			mail;
	private String			firstname;
	private String			lastname;
	
	public Author(String mail, String firstname, String lastname)
	{
		super();
		this.mail = mail;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public String getMail()
	{
		return mail;
	}
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}
	
	public String getFirstname()
	{
		return firstname;
	}
	
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	public String getLastname()
	{
		return lastname;
	}
	
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	public String getUUID()
	{
		return UUID;
	}
	
}
