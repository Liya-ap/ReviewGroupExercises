import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import persistence.HibernateConfig;
import entities.Package;
import daos.PackageDAO;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PackageDAOTest {

    private static PackageDAO packageDAO;
    private static EntityManagerFactory emfTest;

    private Package p1, p2, p3, p4, p5;

    @BeforeAll
    static void setUpAll() {
        emfTest = HibernateConfig.getEntityManagerFactory();
        packageDAO = new PackageDAO(emfTest);
    }

    @AfterAll
    public static void tearDown() {
        emfTest.close();
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emfTest.createEntityManager();
        p1 = new Package();
        p1.setTrackingNumber("ADG356");
        p1.setSenderName("Julie Jørgensen");
        p1.setReceiverName("Per Olsen");
        p1.setDeliveryStatus(Package.DeliveryStatus.PENDING);

        p2 = new Package();
        p2.setTrackingNumber("HAN678");
        p2.setSenderName("Hans Jensen");
        p2.setReceiverName("Emil Karl");
        p2.setDeliveryStatus(Package.DeliveryStatus.PENDING);

        p3 = new Package();
        p3.setTrackingNumber("DDD222");
        p3.setSenderName("Gertrud Lund");
        p3.setReceiverName("Jørgen Borg");
        p3.setDeliveryStatus(Package.DeliveryStatus.IN_TRANSIT);

        p4 = new Package();
        p4.setTrackingNumber("ABC999");
        p4.setSenderName("DAO");
        p4.setReceiverName("Oline");
        p4.setDeliveryStatus(Package.DeliveryStatus.PENDING);

        p5 = new Package();
        p5.setTrackingNumber("HEJ982");
        p5.setSenderName("Simon Funk");
        p5.setReceiverName("Hans Phillip");
        p5.setDeliveryStatus(Package.DeliveryStatus.PENDING);

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Package").executeUpdate();

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);
        em.getTransaction().commit();
    }

    @Test
    public void testPersistPackage() {
        Package pkg = new Package();
        pkg.setTrackingNumber("ABC123");
        pkg.setSenderName("Sender");
        pkg.setReceiverName("Receiver");
        pkg.setDeliveryStatus(Package.DeliveryStatus.PENDING);

        packageDAO.create(pkg);

        Package retrievedPackage = emfTest.createEntityManager().find(Package.class, pkg.getId());
        Assertions.assertNotNull(retrievedPackage);
        Assertions.assertEquals("ABC123", retrievedPackage.getTrackingNumber());
    }

    @Test
    public void testGetById() {

        Package retrievedPackage = packageDAO.getById(p1.getId());

        //Assert
        Assertions.assertEquals("ADG356", retrievedPackage.getTrackingNumber());
    }

    @Test
    public void testGetByTrackingNumber() {
        Package retrievedPackage = packageDAO.getByTrackingNumber(p3.getTrackingNumber());

        //Assert
        Assertions.assertNotNull(retrievedPackage);
        Assertions.assertEquals("DDD222", retrievedPackage.getTrackingNumber());
    }

    @Test
    public void deletePackage(){
        packageDAO.deletePackage(p4);

        Package deletedPackage = packageDAO.getById(p4.getId());
        assertNull(deletedPackage);
    }

    @Test
    public void testUpdateStatus(){

        p1.setDeliveryStatus(Package.DeliveryStatus.IN_TRANSIT);
        packageDAO.updateStatus(p1);

        Package updatedPackage = packageDAO.getByTrackingNumber(p1.getTrackingNumber());
        Assertions.assertNotNull(updatedPackage);
        Assertions.assertEquals(Package.DeliveryStatus.IN_TRANSIT, updatedPackage.getDeliveryStatus());
    }

    @Test
    public void testGetAll() {

        Set<Package> allPackages = packageDAO.getAll();

        Assertions.assertNotNull(allPackages);
        Assertions.assertEquals(5, allPackages.size());
    }
}
