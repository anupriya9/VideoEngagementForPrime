package com.amazon.prime.hackathon.ner;

import java.io.IOException;
import java.util.List;

import com.algorithmia.AlgorithmException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface INamedEntitiesProvider {
	public List<String> getNamedEntities(final String text) throws JsonParseException, JsonMappingException, IOException, AlgorithmException;
}
