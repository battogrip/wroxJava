// Chapter 9 Exercise 3

// Lists the subdirectories of a directory specified by a command line argument
// or all directories if no command line argument is supplied

import java.io.File;
import java.io.FileFilter;
 
public class ListDirectories implements FileFilter {
  private static StringBuffer indent = new StringBuffer();

  public static void main(String[] args) {
    File[] roots = null;

    if(args.length>0) {                               // Check for command line argument                            
      File directory = new File(args[0]);
      if(directory.isDirectory()) {                   // If it is a directory
        roots = new File[1];                          // then store it ready for listing
        roots[0] = directory;
      } else {                                        // otherwise issue message and bail out
        System.out.println(directory.getName()+" is not a directory");
        System.exit(1);
      }
    } else {                                           // If no command line argument
      roots = File.listRoots();                        // get the sytem roots
    }
                              
    ListDirectories lister = new ListDirectories();    // Create object to do the listing
    for(File root : roots) {                           // and list all the directories
      lister.listDirectories(root);
    }
  }

  // Lists directories recursively with each subdirectory level indented two spaces
  private void listDirectories(File rootDirectory) {
    String name = rootDirectory.getName();
    if(name.length()==0) {                             // For system roots under windows the name is empty
      name = rootDirectory.getPath();                  // so get the path instead
    }
    System.out.println(indent.toString()+name);        // Output the directory name following the indent
    indent.append("  ");                               // Increase indent for next level
    File[] fileList = rootDirectory.listFiles(this);   // Get subdirectories of current directory
    if(fileList != null) {                             // As long as we have a subdirectory list
      for(File file : fileList) {                      // list the contents
        listDirectories(file);
      }
    }

    indent.delete(indent.length()-2, indent.length()); // reduce the indent 
    return;                                            // before returning 
  }

  // Filter method to only accept directories
  public boolean accept(File file) {
    return file.isDirectory();
  }  

}
