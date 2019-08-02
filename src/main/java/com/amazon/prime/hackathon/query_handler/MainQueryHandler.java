package com.amazon.prime.hackathon.query_handler;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

public class MainQueryHandler {

	private final List<GenericQueryHandler> handlers;
	
	public MainQueryHandler(){
		this.handlers = new LinkedList<GenericQueryHandler>();
		final RewindCommandHandler rewindCommandHandler = new RewindCommandHandler();
		final ForwardCommandHandler forwardCommandHandler = new ForwardCommandHandler();
		handlers.add(new GenericQueryHandler("(?i).*(rewind).*by (?<data>[0-9a-z]{1,20} (second|minute|hour)).*", rewindCommandHandler));
		handlers.add(new GenericQueryHandler("(?i).*(forward).*by (?<data>[0-9a-z]{1,20} (second|minute|hour)).*", forwardCommandHandler));	
	}
	
	final Integer handleQuery(final Integer timestamp, final String command) {
		for(GenericQueryHandler handler : handlers) {
			final Matcher matcher = handler.matcher(command);
			if(matcher.matches()) {
				return handler.getQueryHandler().handle(timestamp,matcher.group("data"));
			}
		}
		return 0;
	}	
}
//rewind
//forward
//take me to the point where this happened
//take me to the point where someone said

