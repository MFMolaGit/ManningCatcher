package com.geva.manningcatcher.dao.read;

import java.util.List;

import com.geva.manningcatcher.beans.New;

public interface ManningReader<T> {
	
	New<T> read();
	
	T read(String field, String value);
	
	List<T> readAll();

}
