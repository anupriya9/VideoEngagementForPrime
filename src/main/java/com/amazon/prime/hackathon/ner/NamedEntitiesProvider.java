package com.amazon.prime.hackathon.ner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.algorithmia.AlgorithmException;
import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NamedEntitiesProvider implements INamedEntitiesProvider{

	private final AlgorithmiaClient client;
	private final Algorithm algo;
	private final ObjectMapper mapper;

	public NamedEntitiesProvider() {
		this.client = Algorithmia.client("simDuU2xvDdiM5oaRFHAcfDTg0r1");
		this.algo = client.algo("nlp/AutoTag/1.0.1");
		algo.setTimeout(300L, java.util.concurrent.TimeUnit.SECONDS); 
		this.mapper = new ObjectMapper();
	}

	public Set<String> getNamedEntities(final String text) throws JsonParseException, JsonMappingException, IOException, AlgorithmException{
		final AlgoResponse result = algo.pipe(text);
		return new HashSet<String>(Arrays.asList(mapper.readValue(result.asJsonString(), String[].class)));
	}
}
