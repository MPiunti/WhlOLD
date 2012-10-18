package org.krams.controller;

import java.io.IOException;
import java.io.InputStreamReader;

import org.krams.domain.upload.UploadItem;
import org.krams.service.CSVService;
import org.krams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.csvreader.CsvReader;
 
 
@Controller
@RequestMapping(value = "/uploader")
public class UploadController
{
	
  @Autowired
  private CSVService service;
	
  @RequestMapping(method = RequestMethod.GET)
  public String getUploadForm(Model model) {
    model.addAttribute(new UploadItem());
    return "uploader/uploadForm";
  }
 
  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView upload(UploadItem uploadItem, BindingResult result) {
    if (result.hasErrors())  {
      for(ObjectError error : result.getAllErrors()) {
        System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
      }
      return new ModelAndView("uploader/uploadForm");
    }
    
    ModelAndView model = new ModelAndView("redirect:/users");
    model.addObject("fname", uploadItem.getName());
    model.addObject("ofname", uploadItem.getFileData().getOriginalFilename());
    model.addObject("ctype", uploadItem.getFileData().getContentType());
    
    // Some type of file processing...
    System.err.println("-------------------------------------------");
    System.err.println("Test upload: name:" + uploadItem.getName());
    System.err.println("Test upload: original Name:" + uploadItem.getFileData().getOriginalFilename());
    System.err.println("Test upload: content-type:" + uploadItem.getFileData().getContentType());
    System.err.println("-------------------------------------------");
    
    InputStreamReader isr;
	try {
		isr = new InputStreamReader(uploadItem.getFileData().getInputStream());
		CsvReader csvDocument = new CsvReader(isr);
        csvDocument.readHeaders();
        for(int i=0;i < csvDocument.getHeaders().length; i++){
        	System.err.println(csvDocument.getHeaders()[i] + ";");
        }
        service.importRows(csvDocument);
        isr.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    return model;
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
    
    // Some type of file processing...
    System.err.println("-------------------------------------------");
    System.err.println("Test upload: name:" + uploadItem.getName());
    System.err.println("Test upload: original Name:" + uploadItem.getFileData().getOriginalFilename());
    System.err.println("Test upload: content-type:" + uploadItem.getFileData().getContentType());
    System.err.println("-------------------------------------------");
    
    InputStreamReader isr;
	try {
		isr = new InputStreamReader(uploadItem.getFileData().getInputStream());
		CsvReader csvDocument = new CsvReader(isr);
        csvDocument.readHeaders();
        for(int i=0;i < csvDocument.getHeaders().length; i++){
        	System.err.println(csvDocument.getHeaders()[i] + ";");
        }
        service.importOpenRows(csvDocument);
        isr.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
 
    return model;
  }
}