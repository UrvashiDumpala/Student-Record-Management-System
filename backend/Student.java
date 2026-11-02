public class Student {

    public int id;

    public String rollNo;
    public String name;
    public String email;
    public String phone;

    public String branch;
    public int year;
    public String section;

    public Student() {}

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", rollNo='" + rollNo + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", branch='" + branch + '\'' +
                ", year=" + year +
                ", section='" + section + '\'' +
                '}';
    }
}
