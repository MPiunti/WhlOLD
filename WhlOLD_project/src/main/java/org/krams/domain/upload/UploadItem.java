package org.krams.domain.upload;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem
{
  private String fileName;
  private int status;
  private CommonsMultipartFile fileData;
 
  public String getName()
  {
    return fileName;
  }
 
  public void setName(String name)
  {
    this.fileName = name;
  }
 
  /**
 * @return the status
 */
public int getStatus() {
	return status;
}

/**
 * @param status the status to set
 */
public void setStatus(int status) {
	this.status = status;
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