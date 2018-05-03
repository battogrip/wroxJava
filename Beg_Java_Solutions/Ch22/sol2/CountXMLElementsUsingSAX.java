// Chapter 22 Exercise 2
// Use a SAX parser to count XML elements. Allow specific element types to be specified
// as command line arguments following the file name. If element types are specified on
// the command line, only count eleents of thoe specified types.
/*
  Extra command line arguments are passed as an array of type String[]to a new
  constructor for the ElementCounter inner class. This constructor stores the array
  reference in the elementTypes field for the object. If there are no extra command
  line arguments, the default constructor is called and so the elementTypes field
  will be null.

  The inner ElementCounter class now has a  validElement() method that return true
  if the elementTypes field is null, or if the name passed to it is equal to an element
  of the elementTypes array. 
  
  The validElement() method is called at the beginning of the statElement() method to 
  decide whether or not the current element should be counted.
*/

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Iterator;

public class CountXMLElementsUsingSAX {
  public static void main(String args[]) {
    if(args.length == 0) {
      System.out.println("No file to process. Usage is:"
                                                 +"\njava CountXMLElements \"filename\" ");
      return;
    }
    File file = new File(args[0]);
    SAXParserFactory spf = SAXParserFactory.newInstance();
    SAXParser parser = null;
    spf.setNamespaceAware(true);
    spf.setValidating(true);

    // Create a different ElementCounter object depending on whether types to count are specified
    ElementCounter elementCounter = null; 
    if(args.length>1) {
      String[] elementTypes = new String[args.length-1];
      for(int i = 1 ; i<args.length ; i++)
        elementTypes[i-1] = args[i]; 
      elementCounter = new ElementCounter(elementTypes);
    } else {
      elementCounter = new ElementCounter();
    }

    try {
     parser = spf.newSAXParser();
     System.out.println("Parser object is: "+ parser);
    } catch(SAXException e) {
        e.printStackTrace(System.err);
        System.exit(1);    
    } catch(ParserConfigurationException e) {
        e.printStackTrace(System.err);
        System.exit(1);    
    }

    System.out.println("\nStarting processing of "+file+"\n");
    try {
       parser.parse(file, elementCounter);
    } catch(IOException e) {
        e.printStackTrace(System.err);
    } catch(SAXException e) {
        e.printStackTrace(System.err);
    }
    elementCounter.listElementCounts();
  }

  // Defines a SAX handler to count elements
  static class ElementCounter extends DefaultHandler {
    // Constructor used when types are specified on the command line
    public ElementCounter(String[] types) {
      elementTypes = types;
    }

    // Because we included the constructor above we must also define this constructor
    public ElementCounter() { }

    public void startDocument() {
      System.out.println("Starting document processing... ");
    }    

    public void endDocument()  {
      System.out.println("Document processing finished. ");
    }
  
    // Check that we should be processing this element
    private boolean validElement(String name) {
      if(elementTypes == null) {
        return true;
      }
        
      // Check for one of the types to be counted
      for(String elementType : elementTypes) {
        if(name.equals(elementType)) {
          return true;
        }
      }
      return false;              // It's not a type to count
    }

    public void startElement(String uri, String localName, String qname, 
                                                               Attributes attr) {
      // If one or more element types are specified, only count those
      if(!validElement(localName)) {
        return;
      }
      ElementRecord element = findElement(localName);
      if(element == null) {
        elementRecords.add(new ElementRecord(localName));
      } else {
        element.increment();
      }
    }
  
    private ElementRecord findElement(String name) {
      for(ElementRecord element : elementRecords) {
        if(name.equals(element.getName())) {
          return element;
        }
      }
      return null;
    }
    
    public void listElementCounts() {
      for(ElementRecord element : elementRecords) {
        System.out.println(element);
      }
    }
  
    public void ignorableWhitespace(char[] ch, int start, int length) {
      // Uncomment below to see that this is happening
      // System.out.println("Ignoring ignorable whitespace... ");
    }

    // Defines an object recording the count for an element
    class ElementRecord {
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

    private Vector<ElementRecord> elementRecords = new Vector<ElementRecord>();
    private String[] elementTypes = null;
  }
}