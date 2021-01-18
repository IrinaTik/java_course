package Tables;

import Helpers.LinkedPurchaseId;

import javax.persistence.*;

@Entity(name = "LinkedPurchaseList")
public class LinkedPurchase {

    @EmbeddedId
    private LinkedPurchaseId id;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Student.class)
//    @JoinColumn(name = "student_id", insertable = false, updatable = false)
//    private Student student;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Course.class)
//    @JoinColumn(name = "course_id", insertable = false, updatable = false)
//    private Course course;

    public LinkedPurchaseId getId() {
        return id;
    }

    public void setId(LinkedPurchaseId id) {
        this.id = id;
    }

//    public Student getStudent() {
//        return student;
//    }
//
//    public Course getCourse() {
//        return course;
//    }
//
    @Override
    public String toString() {
        return id.getStudentId() + " - " + id.getCourseId();
    }
}
