package com.geva.manningcatcher.dao.read;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.geva.manningcatcher.beans.New;
import com.geva.manningcatcher.beans.Pack;
import com.geva.manningcatcher.dao.MongoManningConnector;
import com.geva.manningcatcher.utils.Constants;
import com.geva.manningcatcher.utils.PackNodes;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class PackManningReader implements ManningReader<Pack>, PackNodes {

	private MongoCollection<Document> collection;
	
	public PackManningReader() {
		collection = MongoManningConnector.connect(Constants.MONGO_PACKS_COLLECTION);
	}
	
	@Override
	public New<Pack> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pack read(String id) {
		ObjectId packId = new ObjectId(id);
		List<Document> packsFound = collection.find(new Document(PACKID, packId)).into(new ArrayList<Document>());
		Pack pack = new Pack();
		
		if(!packsFound.isEmpty()) {
			Document dPack = packsFound.get(0); 
			pack.setId(dPack.getObjectId(PACKID));
			pack.setBooks((List<String>) dPack.get(BOOKS));
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
		    	
		    	List<String> books = (List<String>) result.get(BOOKS);
		    	
		    	pack.setTimes(result.getInteger(TIMES));
		    	
		    	packs.add(pack);
		    }
		});
		
		return packs;
	}

}
