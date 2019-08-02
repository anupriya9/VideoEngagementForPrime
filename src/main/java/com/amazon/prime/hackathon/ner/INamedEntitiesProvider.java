package com.amazon.prime.hackathon.ner;

import java.io.IOException;
import java.util.Set;

import com.algorithmia.AlgorithmException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface INamedEntitiesProvider {
	public Set<String> getNamedEntities(final String text) throws JsonParseException, JsonMappingException, IOException, AlgorithmException;
}
