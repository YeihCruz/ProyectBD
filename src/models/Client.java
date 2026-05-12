package models;

public class Client {

    private int clientId;
    private int agencyId;
    private int genderId;
    private int countryId;
    private String name;
    private String lastName;
    private String identificationNumber;
    private int age;
    private String address;
    private String phone;
    private String email;


    // Full constructor
    public Client(int clientId,
                  int agencyId,
                  int genderId,
                  int countryId,
                  String name,
                  String lastName,
                  String identificationNumber,
                  int age,
                  String address,
                  String phone,
                  String email) {

        this.clientId = clientId;
        this.agencyId = agencyId;
        this.genderId = genderId;
        this.countryId = countryId;
        this.name = name;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }
}