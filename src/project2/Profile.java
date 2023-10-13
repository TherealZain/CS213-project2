package project2;

public class Profile implements Comparable<Profile>{
private String fname;
private String lname;
private Date dob;
private static final int EQUAL_CONDITION = 0;

    public Profile(String fName, String lName, Date dob) {
        this.fname = fName;
        this.lname = lName;
        this.dob = dob;
    }

    public String getFname(){
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Date getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile){
            Profile o = (Profile) obj;
            return fname.equals(o.fname) && lname.equals(o.lname)
                    && dob.compareTo(o.dob) == EQUAL_CONDITION;
        }
        return false;

    }

    @Override
    public int compareTo(Profile o) {
        if(this.lname.compareTo(o.lname) > 0){
            return 1;
        }
        if(this.fname.compareTo(o.fname) < 0){
            return -1;
        }
        if (this.dob.compareTo(o.dob) > 0) {
            return 1;
        }

        return 0;
    }
}
