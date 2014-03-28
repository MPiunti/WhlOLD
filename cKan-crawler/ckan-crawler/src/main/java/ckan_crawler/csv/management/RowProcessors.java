package ckan_crawler.csv.management;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class RowProcessors {
	
	/**
	 * Documents
	 * @return
	 */
	public static CellProcessor[] getDocProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
	                new UniqueHashCode(), // name (id)(must be unique)
	                new NotNull(), // title 
	                new Optional(), // author
	                new Optional(), // author_email
	                new Optional() // URL
		};
	    return processors;
	}
	final public static String[] docHeader = new String[] { "name", "title", "author", "author_email", "url" };
	
	
	/**
	 * Organizations
	 * @return
	 */
	public static CellProcessor[] getOrgProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
                new UniqueHashCode(), // org_id (must be unique)
                new NotNull() // description
        };
		return processors;
    }
	final public static String[] orgHeader = new String[] { "org_id", "description"};
	
	
	/**
	 * Licenses
	 * @return
	 */
	public static CellProcessor[] getLicProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
                new UniqueHashCode(), // name (must be unique)
                new NotNull() // license_title
        };
		return processors;
    }
	final public static String[] licHeader = new String[] { "license_id", "license_title"};
	
	
	/**
	 * Tags
	 * @return
	 */
	public static CellProcessor[] getTagProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
                new UniqueHashCode(), // tag_id (must be unique)
                new NotNull(), // name
                new Optional()  // display_name
        };
		return processors;
    }
	final public static String[] tagHeader = new String[] { "tag_id", "name", "display_name"};
	
	/**
	 * Tags
	 * @return
	 */
	public static CellProcessor[] getRelProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
                new NotNull(), // name (must be unique)
                new NotNull(), // title
                new NotNull()    // type
        };
		return processors;
    }
	final public static String[] relHeader = new String[] { "node_id", "node_id", "type"};


}
