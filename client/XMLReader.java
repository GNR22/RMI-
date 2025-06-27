import javax.xml.parsers.DocumentBuilderFactory;  // --> Provides methods to create XML document builders
import org.w3c.dom.*;                             // --> Provides classes to work with XML document structure
import java.io.File;                              // --> Represents the XML file
import java.util.*;                               // --> Provides List and Map interfaces and utility classes

/*Brief expl: This class (XMLReader) reads an XML file, parses its elements, and converts each XML record 
(like a student or course) into a Map<String, String>. It returns a List of these maps representing all records
from the XML. Basically this is where parsing happens*/
public class XMLReader {

    public static List<Map<String, String>> readXML(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();  // --> List to hold all parsed records

        
        try {
            // Parse the XML file and normalize the document
            Document doc = DocumentBuilderFactory.newInstance()
                              .newDocumentBuilder()
                              .parse(new File(filePath));
            doc.getDocumentElement().normalize();  // --> Normalize the XML structure (optional but recommended)

            NodeList nodeList = doc.getDocumentElement().getChildNodes();  // --> Get all child nodes (e.g. <student> or <course>)

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {  // --> Only process element nodes
                    Element element = (Element) nodeList.item(i);
                    Map<String, String> row = new HashMap<>();  // --> Holds one record (student or course)

                    NodeList fields = element.getChildNodes();  // --> Get child elements of the current element

                    for (int j = 0; j < fields.getLength(); j++) {
                        if (fields.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element field = (Element) fields.item(j);
                            row.put(field.getTagName(), field.getTextContent());  // --> Store tag name and its value
                        }
                    }

                    data.add(row);  // --> Add the record to the data list
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // --> Handle parsing or I/O errors
        }

        return data;  // --> Return the list of parsed records
    }
}
