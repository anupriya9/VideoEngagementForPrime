package com.amazon.prime.hackathon.ner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.algorithmia.AlgorithmException;

public class AudioDescriptionDataProvider implements IAudioDescriptionDataProvider{

	public static void main(String[] args) {
		AudioDescriptionDataProvider provider = new AudioDescriptionDataProvider(new NamedEntitiesProvider());
		Map<Integer, Set<String>> data = provider.getData("goodomens");
		data.keySet().stream().forEach(key->{
			System.out.println(key + " " + data.get(key));
		});
		Map<Integer, Set<String>> data2 = provider.getData("goodomens");
		data2.keySet().stream().forEach(key->{
			System.out.println(key + " " + data2.get(key));
		});
	}

	private static final String AD_FILE_FORMAT = "\\[(?<timestamp>.{4,8})\\] (?<line>.*)";
	private final Pattern _ADPattern;
	private final INamedEntitiesProvider namedEntitiesProvider;
	private final Map<String, Map<Integer,Set<String>>> resultMaps;
	private final String[] videoNames = {"goodomens"};
	
	public AudioDescriptionDataProvider(final INamedEntitiesProvider namedEntitiesProvider) {
		this._ADPattern = Pattern.compile(AD_FILE_FORMAT);
		this.namedEntitiesProvider = namedEntitiesProvider;
		this.resultMaps = new HashMap<String, Map<Integer,Set<String>>>();
		init();
	}

	private void init() {
		Arrays.stream(videoNames).forEach(videoName->{
			final Map<Integer,Set<String>> resultMap = new TreeMap<Integer, Set<String>>();
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(videoName+ ".AD"));
				String fileLine = reader.readLine();
				while (fileLine != null) {
					final Matcher matcher = _ADPattern.matcher(fileLine);
					if(matcher.matches()) {
						final String timestampStr = matcher.group("timestamp");
						final String line = matcher.group("line").replaceAll("\\.", "");
						final Integer timestamp = toIntTimestamp(timestampStr);
						Set<String> namedEntities;
						try {
							System.out.println(line);
							namedEntities = namedEntitiesProvider.getNamedEntities(line);
							resultMap.put(timestamp, namedEntities);
						} catch (AlgorithmException e) {
							e.printStackTrace();
						}	
					}
					fileLine = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			resultMaps.put(videoName, resultMap);
		});

	}

	public Map<Integer,Set<String>> getData(final String videoId){
		final String adFileName = videoId.replaceAll(" ", "").toLowerCase();
		return resultMaps.get(adFileName);
	}

	private static Integer toIntTimestamp(final String timestampStr) {
		final String[] timeUnits = timestampStr.split(":");

		if(timeUnits.length == 2) {
			return ( Integer.parseInt(timeUnits[0]) * 60 ) + ( Integer.parseInt(timeUnits[1]) );
		}

		if(timeUnits.length == 3) {
			return ( Integer.parseInt(timeUnits[0]) * 60 * 60 ) + ( Integer.parseInt(timeUnits[1]) * 60 ) +
					( Integer.parseInt(timeUnits[2]));
		}
		return null;
	}
}
