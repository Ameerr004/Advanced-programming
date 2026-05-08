package Final;



public class Car {
    private String make;
    private String model;
    private String plate;
    private Customer owner;

    public Car(String make, String model, String plate, Customer owner) {
        this.make = make;
        this.model = model;
        this.plate = plate;
        this.owner = owner;
    }

    public String getPlate() {
        return plate;
    }

    @Override
    public String toString() {
        return plate + " (" + make + " " + model + ")";
        
    }
    /** so your facade can call x.getOwner() */
    public Customer getOwner() {
        return owner;
    }

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

   
  
}
