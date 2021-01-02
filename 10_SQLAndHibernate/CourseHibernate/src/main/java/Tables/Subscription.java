package Tables;

import Helpers.SubId;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Subscriptions")
public class Subscription implements Serializable {

    @EmbeddedId
    private SubId subId;

    @Column(name = "subscription_date")
    private Date subDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id",insertable = false, updatable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id",insertable = false, updatable = false)
    private Course course;

    public String getSubId() {
        return subId.toString();
    }

    public void setSubId(SubId subId) {
        this.subId = subId;
    }

    public Date getSubDate() {
        return subDate;
    }

    public String getSubDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(subDate);
    }

    public void setSubDate(Date subDate) {
        this.subDate = subDate;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return subId.toString() + ", subDate=" + getSubDateString();
    }
}
