package one.microstream.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.serializer.reference.Lazy;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Books
{
	private final Map<String, Lazy<List<Book>>>	booksByISBN		= new HashMap<String, Lazy<List<Book>>>();
	
	public Map<String, Lazy<List<Book>>> getBooksByISBN()
	{
		return booksByISBN;
	}
	
	
}
