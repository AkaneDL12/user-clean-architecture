package org.cat.user.clean.architecture.domain.model;

public class User {
    private String firstName;
    private String LastName;
    private String email;
    private String phone;

    //Contructor
    public User(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        LastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    //Metodos sobreescritos
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    //Otros metodos publicos o privados
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
