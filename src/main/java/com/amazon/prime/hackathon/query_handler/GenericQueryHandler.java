package com.amazon.prime.hackathon.query_handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericQueryHandler {

	private final Pattern pattern;
	private final IQueryHandler queryHandler;

	public GenericQueryHandler(final String patternRegex, final IQueryHandler queryHandler) {
		this.queryHandler = queryHandler;
		this.pattern = Pattern.compile(patternRegex);
	}

	public Matcher matcher(final String command) {
		return pattern.matcher(command);
	}

	public IQueryHandler getQueryHandler() {
		return queryHandler;
	}

}
