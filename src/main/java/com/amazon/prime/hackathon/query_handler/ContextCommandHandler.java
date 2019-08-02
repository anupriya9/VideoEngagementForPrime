package com.amazon.prime.hackathon.query_handler;

import com.amazon.prime.hackathon.ner.IAudioDescriptionDataProvider;

public class ContextCommandHandler implements IQueryHandler{


	private final IAudioDescriptionDataProvider audioDescriptionDataProvider;
	public ContextCommandHandler(final IAudioDescriptionDataProvider audioDescriptionDataProvider) {
		this.audioDescriptionDataProvider = audioDescriptionDataProvider;
	}

	@Override
	public Integer handle(Integer currentTimeStamp, String data) {
		
		
		//algo to search here
		
		return currentTimeStamp;
	}
	
}
