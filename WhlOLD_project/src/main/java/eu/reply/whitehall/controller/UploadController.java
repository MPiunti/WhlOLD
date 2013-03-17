package eu.reply.whitehall.controller;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.csvreader.CsvReader;

import eu.reply.whitehall.domain.nodes.OpenDocument;
import eu.reply.whitehall.domain.upload.UploadItem;
import eu.reply.whitehall.service.CSVService;
import eu.reply.whitehall.service.OpenDocumentService;
import eu.reply.whitehall.service.UserService;
 
 
@Controller
@RequestMapping(value = "/uploader")
public class UploadController {
	
  @Autowired
  private CSVService csvService;
  
  @Autowired
  private OpenDocumentService openDocService; 
  
  @Autowired
  private UserService userService;
  
  
	
  @RequestMapping(method = RequestMethod.GET)
  public String getUploadForm(Model model) {
    model.addAttribute(new UploadItem());
    return "uploader/uploadForm";
  }
 
 
  
  //-----------------------------------------------------------------------------
  
  @RequestMapping(value="/open", method = RequestMethod.GET)
  public String getOpenUploadForm(Model model) {
    model.addAttribute(new UploadItem());
    return "uploader/open";
  }  
  
  @RequestMapping(value="/open", method = RequestMethod.POST)
  public ModelAndView uploadOpenData(UploadItem uploadItem, BindingResult result) {
    if (result.hasErrors())  {
      for(ObjectError error : result.getAllErrors()) {
        System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
      }
      return new ModelAndView("uploader/open");
    }
    
    ModelAndView model = new ModelAndView("redirect:/open");
    model.addObject("fname", uploadItem.getName());
    model.addObject("ofname", uploadItem.getFileData().getOriginalFilename());
    model.addObject("ctype", uploadItem.getFileData().getContentType());

    OpenDocument openDocument = new OpenDocument(uploadItem.getName());
    openDocService.create(openDocument, userService.getUserFromSession());
    
    /* Some kind of file pre-processing...
    System.err.println("-------------------------------------------");
    System.err.println("Test upload: name:" + uploadItem.getName());
    System.err.println("Test upload: original Name:" + uploadItem.getFileData().getOriginalFilename());
    System.err.println("Test upload: content-type:" + uploadItem.getFileData().getContentType());
    System.err.println("-------------------------------------------");
    */
    
    InputStreamReader isr;
	try {
		isr = new InputStreamReader(uploadItem.getFileData().getInputStream());
		CsvReader csvReader = new CsvReader(isr);
       /* csvDocument.readHeaders();
        for(int i=0;i < csvDocument.getHeaders().length; i++){
        	System.err.println(csvDocument.getHeaders()[i] + ";");
        }*/

        csvService.importOpenRows(csvReader,openDocument);
        isr.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
 
    return model;
  }
  
  
//-----------------------------------------------------------------------------
  
  @RequestMapping(value="/data", method = RequestMethod.GET)
  public String getOpenDataUploadForm(Model model) {
    model.addAttribute(new UploadItem());
    return "uploader/data";
  }  
  
  @RequestMapping(value="/data", method = RequestMethod.POST)
  public ModelAndView uploadOpenDataDocument(UploadItem uploadItem, BindingResult result) {
    if (result.hasErrors())  {
      for(ObjectError error : result.getAllErrors()) {
        System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
      }
      return new ModelAndView("uploader/data");
    }
    
    ModelAndView model = new ModelAndView("redirect:/home/u");
    
    InputStreamReader isr;
	try {
		isr = new InputStreamReader(uploadItem.getFileData().getInputStream());
		CsvReader csvDocument = new CsvReader(isr);
       /* csvDocument.readHeaders();
        for(int i=0;i < csvDocument.getHeaders().length; i++){
        	System.err.println(csvDocument.getHeaders()[i] + ";");
        }*/
        String doc_name = uploadItem.getName().replace(" ", "_");
        csvService.importOpenData(csvDocument, doc_name, uploadItem.getStatus() );
        isr.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
 
    return model;
  }
}