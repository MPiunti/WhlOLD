package eu.reply.whitehall.csvIO;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * Model and View CSV rendering
 * @author mPiunti
 * @date 2013
 */
public class CSVView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> modelMap,
			HttpServletRequest req, HttpServletResponse response) throws Exception {

		
		BufferedWriter writer = new BufferedWriter(response.getWriter());		
		response.setHeader("Content-Disposition","attachment; filename=\"csv_table.csv\"");
		Object SEPARATOR = modelMap.get("SEPARATOR");
		// get data from model
		List<List<String>> it = (List<List<String>>)modelMap.get("datasheet");
		
		// loop over data
		int i =0;
		for(List<String> row : it){
			++i;
			writer.write(""+i);
			for(String atom : row){
				writer.write(""+SEPARATOR + atom); 				
			}
			writer.newLine();
		}
		writer.flush();
		writer.close();		
	}

}
