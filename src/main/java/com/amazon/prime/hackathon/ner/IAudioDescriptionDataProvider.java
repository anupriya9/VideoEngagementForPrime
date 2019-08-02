package com.amazon.prime.hackathon.ner;

import java.util.Map;
import java.util.Set;

public interface IAudioDescriptionDataProvider {
	public Map<Integer,Set<String>> getData(final String videoId);
}
