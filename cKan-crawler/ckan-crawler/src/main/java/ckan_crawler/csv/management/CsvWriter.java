package ckan_crawler.csv.management;

import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

public class CsvWriter {
	
	
	
	public static void writeDocNodes(LinkedHashMap<String, LinkedList<String>> docNodes)
			throws Exception{
		ICsvListWriter listWriter = null;
		try {
			listWriter = new CsvListWriter(new FileWriter("docNodes.csv"), CsvPreference.TAB_PREFERENCE);		                
			// the header elements are used to map the bean values to each column (names must match)
			final CellProcessor[] processors = RowProcessors.getDocProcessors();
			// write the header
			listWriter.writeHeader(RowProcessors.docHeader);	                
			// write the beans
			for( final LinkedList<String> node : docNodes.values() ) {
				listWriter.write(node, processors);
			}		                
		}
		finally {
			if( listWriter != null ) {
				listWriter.close();
				}
			}
		}
	
	public static void writeOrgNodes(LinkedHashMap<String, LinkedList<String>> orgNodes)
			throws Exception{
		ICsvListWriter listWriter = null;
		try {
			listWriter = new CsvListWriter(new FileWriter("orgNodes.csv"), CsvPreference.TAB_PREFERENCE);		                
			// the header elements are used to map the bean values to each column (names must match)
			final CellProcessor[] processors = RowProcessors.getOrgProcessors();
			// write the header
			listWriter.writeHeader(RowProcessors.orgHeader);	                
			// write the beans
			for( final LinkedList<String> node : orgNodes.values() ) {
				listWriter.write(node, processors);
			}		                
		}
		finally {
			if( listWriter != null ) {
				listWriter.close();
				}
			}
	}
	
	
	public static void writeLicNodes(LinkedHashMap<String, LinkedList<String>> licNodes)
			throws Exception{
		ICsvListWriter listWriter = null;
		try {
			listWriter = new CsvListWriter(new FileWriter("licNodes.csv"), CsvPreference.TAB_PREFERENCE);		                
			// the header elements are used to map the bean values to each column (names must match)
			final CellProcessor[] processors = RowProcessors.getLicProcessors();
			// write the header
			listWriter.writeHeader(RowProcessors.licHeader);	                
			// write the beans
			for( final LinkedList<String> node : licNodes.values() ) {
				listWriter.write(node, processors);
			}		                
		}
		finally {
			if( listWriter != null ) {
				listWriter.close();
				}
			}
	}
	
	public static void writeTagNodes(LinkedHashMap<String, LinkedList<String>> tagNodes)
			throws Exception{
		ICsvListWriter listWriter = null;
		try {
			listWriter = new CsvListWriter(new FileWriter("tagNodes.csv"), CsvPreference.TAB_PREFERENCE);		                
			// the header elements are used to map the bean values to each column (names must match)
			final CellProcessor[] processors = RowProcessors.getTagProcessors();
			// write the header
			listWriter.writeHeader(RowProcessors.tagHeader);	                
			// write the beans
			for( final LinkedList<String> node : tagNodes.values() ) {
				listWriter.write(node, processors);
			}		                
		}
		finally {
			if( listWriter != null ) {
				listWriter.close();
				}
			}
	}
	
	
	public static void writeRelationships(LinkedHashMap<String, LinkedList<String>> rels)
			throws Exception{
		ICsvListWriter listWriter = null;
		try {
			listWriter = new CsvListWriter(new FileWriter("relationships.csv"), CsvPreference.TAB_PREFERENCE);		                
			// the header elements are used to map the bean values to each column (names must match)
			final CellProcessor[] processors = RowProcessors.getRelProcessors();
			// write the header
			listWriter.writeHeader(RowProcessors.relHeader);	                
			// write the beans
			for( final LinkedList<String> node : rels.values() ) {
				listWriter.write(node, processors);
			}		                
		}
		finally {
			if( listWriter != null ) {
				listWriter.close();
				}
			}
	}

}
