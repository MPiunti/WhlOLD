<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Open Data - Upload</title>
    </head>
    <body>
        <div>
           <strong>Upload Document:</strong>
           <br/>
		   <br/>
        </div>
    
        <form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Upload Fields</legend>
 
                <p>
                    <form:label for="name" path="name">Name</form:label><br/>
                    <form:input path="name"/>
                </p>
                <p>
                   <form:label for="status" path="status">Stato</form:label><br/>
                   <form:select path="status">
						<option value="">Seleziona Stato</option>
						<option value="0">Visibile</option>
						<option value="1">Privato</option>
				   </form:select>
                </p> 
                <p>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
 
                <p>
                    <input type="submit" />
                </p>
 
            </fieldset>
        </form:form>
    </body>
</html>