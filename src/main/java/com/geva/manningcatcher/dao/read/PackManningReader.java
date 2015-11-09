package com.geva.manningcatcher.dao.read;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.Book;
import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.BookNodes;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.PackNodes;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class PackManningReader implements ManningReader<Pack>, PackNodes {

	private BookManningReader bookManningReader;
	
	private MongoCollection<Document> collection;
	
	public PackManningReader() {
		bookManningReader = new BookManningReader();
		collection = MongoManningConnector.connect(Constants.MONGO_PACKS_COLLECTION);
	}
	
	@Override
	public New<Pack> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pack read(String field, String value) {
		ObjectId packId = new ObjectId(value);
		List<Document> packsFound = collection.find(new Document(field, packId)).into(new ArrayList<Document>());
		Pack pack = new Pack();
		
		if(!packsFound.isEmpty()) {
			Document dPack = packsFound.get(0); 
			pack.setId(dPack.getObjectId(PACKID));
			
			List<ObjectId> bookIds = (List<ObjectId>) dPack.get(BOOKS);
			List<Book> books = new ArrayList<Book>();
			
				for(ObjectId bookId:bookIds) {
					books.add(bookManningReader.read(BookNodes.BOOKID, bookId.toHexString()));
				}
			
			pack.setBooks(books);
			pack.setTimes(dPack.getInteger(TIMES));
		}
			
		return pack;
	}

	@Override
	public List<Pack> readAll() {
		FindIterable<Document> packsFound = collection.find();
		final List<Pack> packs = new ArrayList<Pack>();
		
		packsFound.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document result) {
		    	Pack pack = new Pack();
		    	
		    	pack.setId(result.getObjectId(PACKID));
		    	
				List<String> bookIds = (List<String>) result.get(BOOKS);
				List<Book> books = new ArrayList<Book>();
				
					for(String bookId:bookIds) {
						books.add(bookManningReader.read(BookNodes.BOOKID, bookId));
					}
		    	
				pack.setBooks(books);
		    	pack.setTimes(result.getInteger(TIMES));
		    	packs.add(pack);
		    }
		});
		
		return packs;
	}

}
