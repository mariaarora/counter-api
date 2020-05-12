package com.test.knowledgetest.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class CounterService {
	private static Path path = Paths.get("src/main/resources/sample-paragrah.txt");
	
	private static List<String> wordList;
	static{
		try {
			wordList = Files.lines(path)
					 .flatMap((String line) -> Stream.of(line.split("[\\p{Punct}\\s]+")))
					 .map(String::toLowerCase)
					 .collect(Collectors.toList());
		} catch (IOException e) {
		}
	}
	
	public List getCount(List<String> wordsToCount){
		List<Map> resultList = new ArrayList<Map>();
		for(String str: wordsToCount){
			HashMap<String, Long> wordMap = new HashMap<String, Long>();
			wordMap.put(str, (wordList.stream()
			.filter(s -> s.equals(str.toLowerCase())).count()));
			resultList.add(wordMap);
		}
		return resultList;
	}
	
	public String getTopNCount(int count){
		StringBuffer sb = new StringBuffer();
		wordList.stream()
		 .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum))
		 .entrySet()
		 .stream()
		 .sorted((a, b) -> a.getValue() == b.getValue() ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue())
		 .limit(count)
		 .forEach(item -> sb.append(item.getKey() + "|" + item.getValue() + "\n"));
		return sb.toString();
		
	}
}
