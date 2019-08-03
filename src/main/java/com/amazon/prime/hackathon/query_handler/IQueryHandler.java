package com.amazon.prime.hackathon.query_handler;

public interface IQueryHandler {

	Integer handle(final String videoId, final Integer currentTimeStamp, final String command);
	
}
