class CreateSpheres {
  public static void main(String[] args) {
    System.out.println("Number of objects = " + Sphere.getCount());

    Sphere ball = new Sphere(4.0, 0.0, 0.0, 0.0);       // Create a sphere
    System.out.println("Number of objects = " + ball.getCount());

    Sphere globe = new Sphere(12.0, 1.0, 1.0, 1.0);     // Create a sphere
    System.out.println("Number of objects = " + Sphere.getCount());

    // Output the volume of each sphere
    System.out.println("ball volume = " + ball.volume());
    System.out.println("globe volume = " + globe.volume());
  }
}
