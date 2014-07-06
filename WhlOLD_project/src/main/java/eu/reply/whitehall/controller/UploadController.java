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
import eu.reply.whitehall.repository.UserRepository;
import eu.reply.whitehall.service.CSVService;
import eu.reply.whitehall.service.OpenDocumentService;
 
 
@Controller
@RequestMapping(value = "/uploader")
public class UploadController {
	
  @Autowired
  private CSVService csvService;
  
  @Autowired
  private OpenDocumentService openDocService; 
  
  @Autowired
  private UserRepository userRepo;

  @RequestMapping(method = RequestMethod.GET)
  public String getUploadForm(Model model) {
    model.addAttribute(new UploadItem());
    return "uploader/uploadForm";
  }
 
 
  
  /*-----------------------------------------------------------------------------
  
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
    openDocService.create(openDocument, userRepo.getUserFromSession());
    
     Some kind of file pre-processing...
    System.err.println("-------------------------------------------");
    System.err.println("Test upload: name:" + uploadItem.getName());
    System.err.println("Test upload: original Name:" + uploadItem.getFileData().getOriginalFilename());
    System.err.println("Test upload: content-type:" + uploadItem.getFileData().getContentType());
    System.err.println("-------------------------------------------");

    
    InputStreamReader isr;
	try {
		isr = new InputStreamReader(uploadItem.getFileData().getInputStream());
		CsvReader csvReader = new CsvReader(isr);
        // csvDocument.readHeaders();
       // for(int i=0;i < csvDocument.getHeaders().length; i++){
        	System.err.println(csvDocument.getHeaders()[i] + ";");
       // }
        csvService.importOpenRows(csvReader,openDocument);
        isr.close();
	} catch (IOException e) {
		e.printStackTrace();
	} 
 
    return model;
  }
  */
  
//------------------- Upload Open Data Document -----------------------------
  
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
    String doc_name = uploadItem.getName().replace(" ", "_");
    OpenDocument openDocument = new OpenDocument(doc_name);
    openDocument.setVisible(uploadItem.getStatus());

    InputStreamReader isr;
	try {
		isr = new InputStreamReader(uploadItem.getFileData().getInputStream());
		CsvReader csvReader = new CsvReader(isr);
       /* csvDocument.readHeaders();
        for(int i=0;i < csvDocument.getHeaders().length; i++){
        	System.err.println(csvDocument.getHeaders()[i] + ";");
        }*/
	    openDocService.create(openDocument, userRepo.getUserFromSession());
	    
        //csvService.importOpenRows(csvReader,openDocument);
        csvService.importOpenData(csvReader, openDocument, userRepo.getUserFromSession());
        isr.close();
        System.err.println("Successfully loaded document: " + doc_name);
        model.addObject("success", doc_name);
	} catch (IOException e) {
		e.printStackTrace();
	} 
 
    return model;
  }
  
  
  // -----------------------------------------------
  // PLUGIN  BLUEIMP
  
  @RequestMapping(value="/fileuploader", method = RequestMethod.GET)
  public String fileUploaderm(Model model) {
    model.addAttribute(new UploadItem());
    return "uploader/fileuploader";
  }  
}
