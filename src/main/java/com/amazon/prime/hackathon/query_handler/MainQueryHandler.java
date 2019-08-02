package com.amazon.prime.hackathon.query_handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

@Component
public class MainQueryHandler {

	private final List<GenericQueryHandler> handlers;
	@Autowired
	private GenericCommandQueryHandler genericCommandQueryHandler;
	
	public MainQueryHandler(){
		this.handlers = new LinkedList<GenericQueryHandler>();
		final RewindCommandHandler rewindCommandHandler = new RewindCommandHandler();
		final ForwardCommandHandler forwardCommandHandler = new ForwardCommandHandler();
		handlers.add(new GenericQueryHandler("(?i).*(rewind).*by (?<data>[0-9a-z]{1,20} (second|minute|hour)).*", rewindCommandHandler));
		handlers.add(new GenericQueryHandler("(?i).*(forward).*by (?<data>[0-9a-z]{1,20} (second|minute|hour)).*", forwardCommandHandler));
		//handlers.add(new GenericQueryHandler("(?<data> *)", genericCommandQueryHandler));
	}
	
	public final Integer handleQuery(final Integer timestamp, final String command) {
		for(GenericQueryHandler handler : handlers) {
			final Matcher matcher = handler.matcher(command);
			if(matcher.matches()) {
				return handler.getQueryHandler().handle(timestamp,matcher.group("data"));
			}
		}
		//final GenericCommandQueryHandler genericCommandQueryHandler = new GenericCommandQueryHandler();
		return genericCommandQueryHandler.handle(timestamp, command);
		//return 0;
	}	
}
//rewind
//forward
//take me to the point where this happened
//take me to the point where someone said

