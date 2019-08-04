package com.amazon.prime.hackathon.query_handler;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.prime.hackathon.ner.AudioDataProvider;
import com.amazon.prime.hackathon.ner.AudioDescriptionDataProvider;
import com.amazon.prime.hackathon.ner.NamedEntitiesProvider;

@Service
public class MainQueryHandler {
	
	private final List<GenericQueryHandler> handlers;
	
	@Autowired
	public MainQueryHandler(final NamedEntitiesProvider namedEntitiesProvider, AudioDescriptionDataProvider audioDescriptionDataProvider, AudioDataProvider audioDataProvider){
		this.handlers = new LinkedList<GenericQueryHandler>();
		final RewindCommandHandler rewindCommandHandler = new RewindCommandHandler();
		final ForwardCommandHandler forwardCommandHandler = new ForwardCommandHandler();
		final ContextLookupCommandHandler contextLookupCommandHandler = new ContextLookupCommandHandler(namedEntitiesProvider, audioDescriptionDataProvider);
		final SpeechLookupCommandHandler speechLookupCommandHandler = new SpeechLookupCommandHandler(namedEntitiesProvider, audioDataProvider);
		handlers.add(new GenericQueryHandler("(?i).*(rewind).*(by|to) (?<data>[0-9a-z\\s]{1,50} (second|minute|hour)).*", rewindCommandHandler));
		handlers.add(new GenericQueryHandler("(?i).*(forward).*(by|to) (?<data>[0-9a-z\\s]{1,50} (second|minute|hour)).*", forwardCommandHandler));	
		handlers.add(new GenericQueryHandler("(?i).*(play|take).*(when|where|from)(?<data>[0-9a-z\\s]*)", contextLookupCommandHandler));
		handlers.add(new GenericQueryHandler("(?i).*(play|take).*(said|says|speaks|speak|talk|talks)(?<data>[0-9a-z\\s]*)", speechLookupCommandHandler));
	}
	
	public final Integer handleQuery(final String videoId, final Integer timestamp, final String command) {
		for(GenericQueryHandler handler : handlers) {
			final Matcher matcher = handler.matcher(command);
			if(matcher.matches()) {
				return handler.getQueryHandler().handle(videoId, timestamp, matcher.group("data"));
			}
		}
		return 0;
	}	
}