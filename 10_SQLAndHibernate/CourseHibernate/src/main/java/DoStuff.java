import Helpers.LinkedPurchaseId;
import Helpers.SessionHelper;
import Tables.*;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.List;

public class DoStuff {

    public static void main(String[] args) {

        SessionHelper sh = new SessionHelper();
        Session session = sh.getSession();

        List<Purchase> purchases = getPurchases(session);

        for (Purchase purchase : purchases) {
            Course course = getIndex(session, "name", purchase.getId().getCourseName(), Course.class);
            Student student = getIndex(session, "name", purchase.getId().getStudentName(), Student.class);
            session.beginTransaction();
            LinkedPurchase linkedPurchase = new LinkedPurchase();
            linkedPurchase.setId(new LinkedPurchaseId(student.getId(), course.getId()));
            session.save(linkedPurchase);
            session.getTransaction().commit();
        }

        List<LinkedPurchase> linkedPurchases = session.createQuery("from Tables.LinkedPurchase", LinkedPurchase.class).getResultList();
        linkedPurchases.forEach(System.out::println);

        sh.stop();
    }

    private static <T> T getIndex(Session session, String fieldName, String fieldValue, Class<T> tClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> root = query.from(tClass);
        query.select(root).where(builder.equal(root.<Integer>get(fieldName), fieldValue));
        return session.createQuery(query).getSingleResult();
    }

    private static void taskTenThree(Session session) {
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
    }

    private static List<Purchase> getPurchases(Session session) {
        return session.createQuery("from Tables.Purchase", Purchase.class).getResultList();
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