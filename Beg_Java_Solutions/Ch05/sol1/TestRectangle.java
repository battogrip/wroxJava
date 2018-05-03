//CHAPTER 5, SOLUTION 1

// This tests the Rectangle class that appears in the same directory as this.

public class TestRectangle {
  public static void main(String args[]) {
    Rectangle[] rects = new Rectangle[4];
    Rectangle enclosing;
    
    // Initialize the rectangles as arbitrary sizes and at arbitrary positions:
    for(int i=0 ; i<rects.length ; i++) {
      rects[i] = new Rectangle(Math.random()*100, Math.random()*100,
					                     Math.random()*100, Math.random()*100);
      System.out.print("Coords of rectangle " + i + " are: ");
      System.out.println(rects[i]);
    }

    // Initialize the enclosing rectangle to be first rectangle
    enclosing = rects[0];
    
    // Combine it with each the other rectangles in turn.
    // This will result in the rectangle that encloses them all.
    for(Rectangle rect : rects)  {
      enclosing = enclosing.encloses(rect);
    }

    System.out.println("\nCoords of Enclosing rectangle are ");
    System.out.println(enclosing);
  }
}