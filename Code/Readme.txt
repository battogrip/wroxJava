The files containing the source code for the examples that appear in the text are organized
by chapter, with all the files for the examples from each chapter being in their own folder.

If an example consists of a single source file, then the source file appears directly in
the chapter folder.

If an example involves more than one source file, then all the source files for the example
are in their own subfolder to the chapter folder. The folder for an example will usually
have a name that is the same as the class name for the example that contains main() in the
case of an application, or the same as the name of the class derived from JApplet in the
case of an applet.

Where examples that use their own package, the package folder  appears as a subfolder to
the folder containing the source files. If you move the package folder to some
"standard" location for your own packages, then it will make it easier to remember
what you need to put in the -classpath command line argument to access the packages.
For example, if you put all your own package folders such as Geometry and Constants
in a folder C:/Packages, the using the string ".;C:/Packages" as the value for the
-classpath option will enable your packages to be found by the compiler or the
Java interpreter.

I have also included a MyPackages subdirectory that contains the final versions of 
the three packages that are used in various examples.

Don't store your own Java files in the SDK directory structure. Putting them somewhere
else will avoid any possibility for confusing the compiler/interpreter.

Have fun!
Ivor Horton
