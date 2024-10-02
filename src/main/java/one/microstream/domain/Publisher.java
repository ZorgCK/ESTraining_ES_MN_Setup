package one.microstream.domain;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Publisher
{
	private final String	UUID	= java.util.UUID.randomUUID().toString();
	private String			mail;
	private String			company;
	
	public Publisher(String mail, String company)
	{
		super();
		this.mail = mail;
		this.company = company;
	}
	
	public String getMail()
	{
		return mail;
	}
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}
	
	public String getCompany()
	{
		return company;
	}
	
	public void setCompany(String company)
	{
		this.company = company;
	}
	
	public String getUUID()
	{
		return UUID;
	}
	
}
