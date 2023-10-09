package project2;

public class Profile implements Comparable<Profile>{
private String fname;
private String lname;
private Date dob;

    public Profile(String firstName, String lastName, Date dateOfBirth) {
        this.fname = firstName;
        this.lname = lastName;
        this.dob = dateOfBirth;
    }
    @Override
    public int compareTo(Profile o) {
        return 0;
    }
}
