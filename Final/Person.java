package Final;


/** Abstract base for people in the system */
public abstract class Person {
    protected String name;
    protected String address;
    protected String nationalID;
    protected String phoneNumber;

    public Person(String name, String address, String nationalID, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.nationalID = nationalID;
        this.phoneNumber = phoneNumber;
    }

    public String getDetails() {
        return String.format("%s (%s), %s, %s", name, nationalID, address, phoneNumber);
    }

    // implement the new interface method:
    public String getNationalID() {
        return nationalID;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}
}