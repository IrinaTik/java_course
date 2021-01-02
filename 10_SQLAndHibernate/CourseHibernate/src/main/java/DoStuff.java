import Helpers.SessionHelper;
import Tables.*;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.SimpleDateFormat;
import java.util.List;

public class DoStuff {

    public static void main(String[] args) {

        SessionHelper sh = new SessionHelper();
        Session session = sh.getSession();

        getFirstCourse(session);
        System.out.println("================== Students ==================");
        getStudentsList(session);
        System.out.println("================== Teachers ==================");
        getTeachersList(session);
        System.out.println("Всего учителей: " + getTeachersCount(session));
        System.out.println("================== Courses info ==================");
        getInfoForCourse(session);
        System.out.println("================== Subscriptions ==================");
        getSubscriptions(session);
        System.out.println("================== Purchases ==================");
        getPurchases(session);

        sh.stop();
    }

    private static void getPurchases(Session session) {
        List<Purchase> purchases = session.createQuery("from Tables.Purchase", Purchase.class).getResultList();
        purchases.forEach(p -> System.out.println(p.toString()));
    }

    private static void getSubscriptions(Session session) {
        List<Subscription> subscriptions = session.createQuery("from Tables.Subscription", Subscription.class).getResultList();
        subscriptions.forEach(s -> System.out.println(s.getStudent().getName() + " - " + s.getCourse().getName() + " - " + s.getSubDateString()));
    }

    private static void getInfoForCourse(Session session) {
        List<Course> courses = session.createQuery("from Tables.Course", Course.class).getResultList();

        for (Course course : courses) {
            System.out.println(course.getName());
            System.out.println("\t" + course.getTeacher().getName());
            course.getStudents().forEach(student -> System.out.println("\t\t" + student.getName()));
        }
    }

    private static Long getTeachersCount(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Teacher.class)));
        return session.createQuery(query).getSingleResult();
    }

    private static void getTeachersList(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = builder.createQuery(Teacher.class);
        criteriaQuery.from(Teacher.class);
        List<Teacher> teachers = session.createQuery(criteriaQuery).getResultList();

        for (Teacher t : teachers) {
            System.out.println(t.getName() + " - " + t.getAge() + " - " + t.getSalary());
        }
    }

    private static void getStudentsList(Session session) {
        List<Student> students = session.createQuery("from Tables.Student", Student.class).getResultList();

        for (Student s : students) {
            System.out.println(s.getName() + " - " + s.getAge() + " - "
                    + new SimpleDateFormat("dd-MM-yyyy").format(s.getRegistrationDate()));
            s.getCourses().forEach(course -> System.out.println("\t" + course.getName()));
        }
    }

    private static void getFirstCourse(Session session) {
        Course course = session.get(Course.class, 1);
        System.out.println(course.getName() + " - " + course.getStudentsCount());
    }

}