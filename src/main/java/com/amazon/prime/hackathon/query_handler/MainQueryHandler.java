package com.amazon.prime.hackathon.query_handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;


import org.springframework.stereotype.Service;

import com.amazon.prime.hackathon.ner.AudioDescriptionDataProvider;
import com.amazon.prime.hackathon.ner.NamedEntitiesProvider;

@Service
public class MainQueryHandler {

	private final List<GenericQueryHandler> handlers;
	@Autowired
	private ContextCommandHandler contextCommandHandler;
	
	public MainQueryHandler(){
		this.handlers = new LinkedList<GenericQueryHandler>();
		final RewindCommandHandler rewindCommandHandler = new RewindCommandHandler();
		final ForwardCommandHandler forwardCommandHandler = new ForwardCommandHandler();

		handlers.add(new GenericQueryHandler("(?i).*(rewind).*(by|to) (?<data>[0-9a-z\\s]{1,20} (second|minute|hour)).*", rewindCommandHandler));
		handlers.add(new GenericQueryHandler("(?i).*(forward).*(by|to) (?<data>[0-9a-z\\s]{1,20} (second|minute|hour)).*", forwardCommandHandler));	
		handlers.add(new GenericQueryHandler("(?i).*(play|take).*(when|where|from)(?<data>[0-9a-z\\s]*)", contextCommandHandler));	
	}
	
	public final Integer handleQuery(final Integer timestamp, final String command) {
		for(GenericQueryHandler handler : handlers) {
			final Matcher matcher = handler.matcher(command);
			if(matcher.matches()) {
				return handler.getQueryHandler().handle(timestamp,matcher.group("data"));
			}
		}
		return 0;
	}	
}