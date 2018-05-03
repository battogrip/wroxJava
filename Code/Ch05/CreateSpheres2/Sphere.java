class Sphere {
  static final double PI = 3.14; // Class variable that has a fixed value
  static int count = 0;          // Class variable to count objects

  // Instance variables
  double radius;                 // Radius of a sphere

  double xCenter;                // 3D coordinates
  double yCenter;                // of the center
  double zCenter;                // of a sphere

  // Class constructor
  Sphere(double theRadius, double x, double y, double z) {
    radius = theRadius;         // Set the radius

    // Set the coordinates of the center
    xCenter = x;
    yCenter = y;
    zCenter = z;
    ++count;                    // Update object count
  }

  // Construct a unit sphere at a point
  Sphere(double x, double y, double z) {
    xCenter = x;
    yCenter = y;
    zCenter = z;
    radius = 1.0;
    ++count;                           // Update object count
  }

  // Construct a unit sphere at the origin
  Sphere() {
    xCenter = 0.0;
    yCenter = 0.0;
    zCenter = 0.0;
    radius = 1.0;
    ++count;                           // Update object count
  }

  // Static method to report the number of objects created
  static int getCount() {
    return count;               // Return current object count
  }

  // Instance method to calculate volume
  double volume() {
    return 4.0/3.0*PI*radius*radius*radius;
  }
}
