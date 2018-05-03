// Chapter 23 Exercise 1
// Use a DOM parser to count XML elements. Allow specific element types to be specified
// as command line arguments following the file name. If element types are specified on
// the command line, only count eleents of thoe specified types.
/*
  This is somewhat similar to the exercise that used SAX to do this. Here we first
  create the Document object using the DOM parser. Then the scanNodes() method works
  all the nodes in the document adding an ElementRecord object to the elementREcords
  vector for each node type. 
  The validElement() method tests whether a particular node name corresponds to an element
  that should be counted.The #text nodes are ignored in the validElement() method.
  
*/
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;

public class CountXMLElementsUsingDOM implements ErrorHandler {
  public static void main(String args[]) {
    if(args.length == 0) {
      System.out.println("No file to process. Usage is:"
                                                 +"\njava CountXMLElements \"filename\" ");
      return;
    }
    File file = new File(args[0]);

    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    builderFactory.setNamespaceAware(true);       // Set namespace aware
    builderFactory.setValidating(true);           // and validating parser feaures

    DocumentBuilder builder = null;
    try {
      builder = builderFactory.newDocumentBuilder();             // Create the parser
      builder.setErrorHandler(new CountXMLElementsUsingDOM());  //Error handler is this class
    }
    catch(ParserConfigurationException e) {
      e.printStackTrace();
    }

    Document xmlDoc = null;

    try {
      xmlDoc = builder.parse(file);              // Create the document object from the file
    }
    catch(SAXException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    // Check for specific element types on the command line 
    if(args.length>1) {                              // If there are any
      elementTypes = new String[args.length-1];      // create an array
      for(int i = 1 ; i<args.length ; i++)
        elementTypes[i-1] = args[i];                 // and store them in it 
    }

    scanNodes(xmlDoc.getDocumentElement());          // Scan the document nodes  
    listElementCounts();                             // Output the element counts
  }

  // List element counts
  private static void listElementCounts() {
    for(ElementRecord element : elementRecords) {
      System.out.println(element);
    }
  }

  // Check whether an element should be counted
  private static boolean validElement(String name){
   // We don't want to count just text
    if(name.equals("#text")) {
      return false;
    }
      
    // It is not text so if there are no elements specified, process it
    // whatever it is
    if(elementTypes == null) {
      return true;
    }
        
    // We have elements specified, so check if it is in the array
    for(String elementType : elementTypes) {
      if(name.equals(elementType)) {
        return true;             // It is, so process it
      }
    }
    return false;                // It isn't so ignore it    
  }

  // Increment the count for the element, name, or create
  // a new ElementRecord object if this is the first occurrence.
  private static void increment(String name) {
    ElementRecord element = findElement(name);
    if(element == null) {
      elementRecords.add(new ElementRecord(name));
    } else {
      element.increment();
    }
  }

  // Returns the ElementRecord object containing name, or null if there is not one
  private static ElementRecord findElement(String name) {
    for(ElementRecord element : elementRecords) {
      if(name.equals(element.getName())) {
        return element;
      }
    }
    return null;
  }

  // Scan a node and all its child nodes to accumulate a count for each node type
  private static void scanNodes(Node node) {
    String nodeName = node.getNodeName();
    if(validElement(nodeName)) {
      increment(nodeName);
    }

    NodeList list = node.getChildNodes();         // Get the list of child nodes
    if(list.getLength() > 0) {                    // As long as there are some...
      for(int i = 0 ; i<list.getLength() ; i++) { //...scan them & their children...
        scanNodes(list.item(i));                  // by calling scanNodes() for each 
      } 
    }         
  }

  // Handle fatal error
  public void fatalError(SAXParseException spe) throws SAXException {
    System.out.println("Fatal error at line "+spe.getLineNumber());
    System.out.println(spe.getMessage());
    throw spe;
  }

  // Handle warnings
  public void warning(SAXParseException spe) {
    System.out.println("Warning at line "+spe.getLineNumber());
    System.out.println(spe.getMessage());
  }

  // Handle regular non-fatal errors
  public void error(SAXParseException spe) {
    System.out.println("Error at line "+spe.getLineNumber());
    System.out.println(spe.getMessage());
  }



  // Defines an object recording the count for an element
  static class ElementRecord {
    public ElementRecord(String name) {
      this.name = name;
    }

    public void increment() {
      ++count;
    }

    public int getCount() {
      return count;
    }
    
    public String getName() {
      return name;
    }
    
    public String toString() {
      return name + " " + count;
    }
          
    private String name = null;
    private int count = 1;    
  }

  private static Vector<ElementRecord> elementRecords = new Vector<ElementRecord>();
  private static String[] elementTypes = null;
}