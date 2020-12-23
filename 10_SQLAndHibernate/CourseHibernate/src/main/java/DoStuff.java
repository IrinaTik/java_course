import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.SimpleDateFormat;
import java.util.List;

public class DoStuff {

    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {

        prepare();

        getFirstCourse();
        System.out.println("================== Students ==================");
        getStudentsList();
        System.out.println("================== Teachers ==================");
        getTeachersList();
        System.out.println("Всего учителей: " + getTeachersCount());

        end();
    }

    private static Long getTeachersCount() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Teacher.class)));
        return session.createQuery(query).getSingleResult();
    }

    private static void getTeachersList() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> criteriaQuery = builder.createQuery(Teacher.class);
        criteriaQuery.from(Teacher.class);
        List<Teacher> teachers = session.createQuery(criteriaQuery).getResultList();

        for (Teacher t : teachers) {
            System.out.println(t.getName() + " - " + t.getAge() + " - " + t.getSalary());
        }
    }

    private static void getStudentsList() {
        List<Student> students = session.createQuery("from Student", Student.class).getResultList();

        for (Student s : students) {
            System.out.println(s.getName() + " - " + s.getAge() + " - "
                    + new SimpleDateFormat("dd-MM-yyyy").format(s.getRegistrationDate()));
        }
    }

    private static void getFirstCourse() {
        Course course = session.get(Course.class, 1);
        System.out.println(course.getName() + " - " + course.getStudentsCount());
    }

    private static void prepare() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        session = sessionFactory.openSession();
    }

    private static void end() {
        session.close();
        sessionFactory.close();
    }

}