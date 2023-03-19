package it.epicode.capstone.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataRetriever {

	public List<String[]> lineReader(String fileName) {
		List<String[]> r = null;
		CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(csvParser).withSkipLines(1).build()) {
			r = reader.readAll();
		} catch (IOException e) {
			log.info(e.getMessage());
		} catch (CsvException e) {
			log.info(e.getMessage());
		}
		return r;
	}
}
