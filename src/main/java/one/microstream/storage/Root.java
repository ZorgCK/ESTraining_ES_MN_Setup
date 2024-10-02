
package one.microstream.storage;

import io.micronaut.serde.annotation.Serdeable;
import one.microstream.domain.Books;


/**
 * MicroStream data root. Create your data model from here.
 *
 * @see <a href="https://manual.docs.microstream.one/">Reference Manual</a>
 */
@Serdeable
public class Root
{
	public Books			books		= new Books();
	
	public Root()
	{
		super();
	}

	public Books getBooks()
	{
		return books;
	}

	public void setBooks(Books books)
	{
		this.books = books;
	}
	
	
	
}
