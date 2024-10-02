package one.microstream.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.queryparser.classic.ParseException;
import org.eclipse.serializer.reference.Lazy;
import org.eclipse.store.storage.types.StorageManager;

import io.micronaut.eclipsestore.RootProvider;
import jakarta.inject.Singleton;
import one.microstream.concurrent.ReadWriteLocked;
import one.microstream.domain.Book;
import one.microstream.domain.lucene.LuceneUtils;
import one.microstream.storage.Root;


@Singleton
public class DAOBookLazy extends ReadWriteLocked
{
	public final RootProvider<Root>	rootProvider;
	private final StorageManager	manager;
	private final LuceneUtils luceneUtils;
	
	DAOBookLazy(final RootProvider<Root> rootProvider, final StorageManager manager, final LuceneUtils luceneUtils)
	{
		this.rootProvider = rootProvider;
		this.manager = manager;
		this.luceneUtils = luceneUtils;
	}
	
	public Book insert(Book book)
	{
		this.write(() ->
		{
			Root root = rootProvider.root();
			
			Lazy<List<Book>> computeIfAbsent = root.getBooks().getBooksByISBN().computeIfAbsent(
				book.getISBN().substring(0, 3),
				b ->
				{
					ArrayList<Book> l = new ArrayList<Book>();
					l.add(book);
					manager.store(root.getBooks().getBooksByISBN());
					return Lazy.Reference(l);
				});
				
			computeIfAbsent.get().add(book);
			manager.store(computeIfAbsent.get());
			
			ArrayList<Book> indexList = new ArrayList<Book>();
			indexList.add(book);
			luceneUtils.updateIndex(indexList);
		});
		return book;
	}
	
	public int countIndices()
	{
		return rootProvider.root().getBooks().getBooksByISBN().keySet().size();
	}
	
	public List<Book> booksByISBN(String isbn)
	{
		return rootProvider.root().getBooks().getBooksByISBN().get(isbn.substring(0, 3)).get();
		
	}
	
	public List<Book> booksByTitle(String search) throws IOException, ParseException
	{
		List<String> searchByTitle = luceneUtils.searchByTitle(search);
		List<String> index = searchByTitle.stream().map(t -> t.substring(0, 3)).collect(Collectors.toList());
		
		List<Book> collect = rootProvider.root().getBooks().getBooksByISBN().entrySet()
            .stream()
            .filter(ent -> index.contains(ent.getKey()))
            .flatMap(m -> m.getValue().get().stream())
            .collect(Collectors.toList());
		
		List<Book> collect2 = collect.stream()
			.filter(b -> searchByTitle.contains(b.getISBN()))
			.collect(Collectors.toList());
		
		return collect2;
	}
}
