import java.io.Writer;
import java.io.PrintWriter;

public class FormattedWriter extends PrintWriter {
  public final static int LEFT_JUSTIFIED  = 1;
  public final static int RIGHT_JUSTIFIED = 2;
  private int width = 0;                         // Field width required for output
  private int justification = RIGHT_JUSTIFIED;

  // Constructor with a specified field width, autoflush, and justification 
  public FormattedWriter(Writer output, boolean autoflush, int width, int justification) {
    super(output, autoflush);                    // Call PrintWriter constructor
    if(width>0) {
      this.width = width;                        // Store the field width
    }
    if(justification == LEFT_JUSTIFIED || justification == RIGHT_JUSTIFIED) {
      this.justification = justification;
    } 
  }

  // Constructor with a specified field width
  public FormattedWriter(Writer output, int width) {
    this(output, false, width, RIGHT_JUSTIFIED);        
  }

  // Constructor with a specified field width and justification
  public FormattedWriter(Writer output, int width, int justification) {
    this(output, false, width, justification);        
  }

  // Constructor with a specified field width and autoflush option 
  public FormattedWriter(Writer output, boolean autoflush, int width) {
    this(output, autoflush, width, RIGHT_JUSTIFIED); 
  }

  // Helper method for output
  private String pad(String str) {
    if(width == 0) {
      return str;
    }
  
    int blanks = width - str.length();           // Number of blanks needed
    StringBuffer result = new StringBuffer();
    if(blanks<0) {
      for(int i = 0 ; i<width ; i++) {
        result.append('X');
      }
      return result.toString();        
    }
    
    if(blanks>0) {
      for(int i = 0 ; i<blanks ; i++) {
        result.append(' ');
      }
   }

    result.insert(justification == LEFT_JUSTIFIED ? 0 : result.length(), str);
    return result.toString();    
  }

  // Output type long formatted in a given width
  public void print(long value) {
    super.print(pad(String.valueOf(value)));     // Pad to width and output
  }

  // Output type int formatted in a given width
  public void print(int value) {
    super.print(pad(String.valueOf(value)));     // Pad to width and output
  }

  // Output type double formatted in a given width
  public void print(double value) {
    super.print(pad(String.valueOf(value)));     // Pad to width and output
  }

  // Output type float formatted in a given width
  public void print(float value) {
    super.print(pad(String.valueOf(value)));     // Pad to width and output
  }

  // Output type short formatted in a given width
  public void print(short value) {
    super.print(pad(String.valueOf(value)));     // Pad to width and output
  }

  // Output type boolean formatted in a given width
  public void print(boolean value) {
    super.print(pad(String.valueOf(value)));     // Pad to width and output
  }

  // Output type String formatted in a given width
  public void print(String str) {
    super.print(pad(str));                       // Pad to width and output
  }

  // Output type long formatted in a given width
  public void println(long value) {
    super.println(pad(String.valueOf(value)));   // Pad to width and output
  }

  // Output type int formatted in a given width
  public void println(int value) {
    super.println(pad(String.valueOf(value)));   // Pad to width and output
  }

  // Output type double formatted in a given width
  public void println(double value) {
    super.println(pad(String.valueOf(value)));   // Pad to width and output
  }

  // Output type float formatted in a given width
  public void println(float value) {
    super.println(pad(String.valueOf(value)));   // Pad to width and output
  }

  // Output type short formatted in a given width
  public void println(short value) {
    super.println(pad(String.valueOf(value)));   // Pad to width and output
  }

  // Output type boolean formatted in a given width
  public void println(boolean value) {
    super.println(pad(String.valueOf(value)));   // Pad to width and output
  }

  // Output type String formatted in a given width
  public void println(String str) {
    super.println(pad(str));                     // Pad to width and output
  }


  // Output type long formatted in a given width
  public void print(long value, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(String.valueOf(value)));     // Pad to width and output
    this.width = saveWidth;
  }

  // Output type int formatted in a given width
  public void print(int value, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(String.valueOf(value)));     // Pad to width and output
    this.width = saveWidth;
  }

  // Output type double formatted in a given width
  public void print(double value, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(String.valueOf(value)));     // Pad to width and output
    this.width = saveWidth;
  }

  // Output type float formatted in a given width
  public void print(float value, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(String.valueOf(value)));     // Pad to width and output
    this.width = saveWidth;
  }

  // Output type short formatted in a given width
  public void print(short value, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(String.valueOf(value)));     // Pad to width and output
    this.width = saveWidth;
  }

  // Output type boolean formatted in a given width
  public void print(boolean value, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(String.valueOf(value)));     // Pad to width and output
    this.width = saveWidth;
  }

  // Output type String formatted in a given width
  public void print(String str, int width) {
    int saveWidth = this.width;
    this.width = width;
    super.print(pad(str));                       // Pad to width and output
    this.width = saveWidth;
  }

  public void setWidth(int width){
    this.width = width;
  }
  
  public int getWidth(int width){
    return width;
  }
}
