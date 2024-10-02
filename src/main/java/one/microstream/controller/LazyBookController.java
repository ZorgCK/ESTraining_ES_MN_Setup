package one.microstream.controller;

import static io.micronaut.scheduling.TaskExecutors.BLOCKING;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import one.microstream.domain.Book;
import one.microstream.dto.DTOBook;
import one.microstream.repository.DAOBookLazy;


@Controller("/lazybooks")
public class LazyBookController
{
	private final DAOBookLazy dao;
	
	public LazyBookController(DAOBookLazy dao)
	{
		this.dao = dao;
	}
	
	@Get("/countIndices")
	HttpResponse<Integer> countIndices()
	{
		return HttpResponse.ok(dao.countIndices());
	}
	
	@Get("/search/{searchTerm}")
	HttpResponse<List<Book>> searchBook(@NonNull @NotBlank @PathVariable String searchTerm)
	{
		try
		{
			return HttpResponse.ok(dao.booksByTitle(searchTerm));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HttpResponse.notFound();
		}
		catch(ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HttpResponse.serverError();
		}
	}
	
	@Get("/{isbn}")
	List<Book> getBookByISBN(@NonNull @NotBlank @PathVariable String isbn)
	{
		return dao.booksByISBN(isbn);
	}
	
	@ExecuteOn(BLOCKING)
	@Post
	HttpResponse<String> create(@NonNull @NotNull @Valid @Body DTOBook dto)
	{
		dao.insert(new Book(dto));
		return HttpResponse.ok("Successfully created");
	}
}
