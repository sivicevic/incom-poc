package hr.incom.backend.information;

import org.w3c.dom.Document;

public interface IInformation
{
	public void prepareInsertFromString(String path);
	
	public void prepareInsertFromDoc(Document doc);
}
