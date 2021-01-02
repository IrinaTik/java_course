package Helpers;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SubId implements Serializable {

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;

    public SubId() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubId subId = (SubId) o;
        return studentId == subId.studentId &&
                courseId == subId.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return "studentId=" + studentId + ", courseId=" + courseId;
    }
}
