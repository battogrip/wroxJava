// Derived stream class to allow an existing object file to be used
/*
  The ObjectOutputStream constructor write a header to the file, regardless of
  whether the file exists or not. This results in an extra header being written at
  the end of an existing file that is opened in append mode. This results in a 
  StreamCorruptedException when reading of any objects added to the file.
  
  One way round this is to use ObjectOutputStream to write a new file, and this derived
  class for an existing file. You can check whether a file exists or not using the 
  exists() method for the File object. You can also check that the file contains data
  by calling the length() method for the File object.
*/
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
public class ReuseObjectOutputStream extends ObjectOutputStream {
  // Constructor
  public ReuseObjectOutputStream(OutputStream out) throws IOException {
    super(out);
  }

  protected void writeStreamHeader() throws IOException {
    reset();
  }
}
