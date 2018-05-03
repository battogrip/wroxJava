// Chapter 22 Exercise 1
// Use a SAX parser to count XML elements
/*
  The inner ElementCounterclass  defines a SAX handler that counts element. For each
  type of element and ElementRecord object is created that records the count for that type.
  ElementRecord objects are stored in the elementRecords field of the ElementCounter object.
  The ElementRecord class is an inner class to the ElementCounter class. 
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
    ElementCounter elementCounter = new ElementCounter(); 
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
    public void startDocument() {
      System.out.println("Starting document processing... ");
    }    

    public void endDocument()  {
      System.out.println("Document processing finished. ");
    }
  
    public void startElement(String uri, String localName, String qname, 
                                                               Attributes attr) {
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
  }
}