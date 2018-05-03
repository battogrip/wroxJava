// Test cloning
public class TestCloning {
  public static void main(String[] args) {
    try {
      PetDog myPet = new PetDog("Fang", "Chihuahua");
      PetDog yourPet = (PetDog)myPet.clone();
      yourPet.setName("Gnasher");                // Change your dog's name
      yourPet.getFlea().setName("Atlas");        // Change your dog's flea's name
      System.out.println("\nYour pet details:\n"+yourPet);
      System.out.println("\nMy pet details:\n"+ myPet);

    } catch(CloneNotSupportedException e) { 
      e.printStackTrace(System.err); 
    }
  }
}
