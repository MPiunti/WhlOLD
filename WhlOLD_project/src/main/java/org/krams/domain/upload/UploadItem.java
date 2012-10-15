package org.krams.domain.upload;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem
{
  private String fileName;
  private CommonsMultipartFile fileData;
 
  public String getName()
  {
    return fileName;
  }
 
  public void setName(String name)
  {
    this.fileName = name;
  }
 
  public CommonsMultipartFile getFileData()
  {
    return fileData;
  }
 
  public void setFileData(CommonsMultipartFile fileData)
  {
    this.fileData = fileData;
  }
}