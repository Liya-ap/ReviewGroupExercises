package daos;

import jakarta.persistence.EntityNotFoundException;
import persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import entities.Package;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageDAO implements IDAO<Package> {
    private EntityManagerFactory emf;

    public PackageDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Package getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Package.class, id);
        }
    }

    @Override
    public Package getByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Package pkg = em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getSingleResult();
            em.getTransaction().commit();
            return pkg;
        }
    }

    @Override
    public Set<Package> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Package> query = em.createQuery("SELECT p FROM Package p", Package.class);
            List<Package> packageList = query.getResultList();
            return packageList.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void create(Package pkg) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(pkg);
            em.getTransaction().commit();
        }
    }

    @Override
    public void updateStatus(Package pkg) {
        try (EntityManager em = emf.createEntityManager()) {
            Package found = em.find(Package.class, pkg.getId());
            if(found == null) {
                throw new EntityNotFoundException();
            }
            em.getTransaction().begin();
            if (pkg.getDeliveryStatus() != null) {
                found.setDeliveryStatus(pkg.getDeliveryStatus());
            }
            em.merge(found);
            em.getTransaction().commit();
        }
    }

    @Override
    public void deletePackage(Package pkg) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(pkg);
            em.getTransaction().commit();
        }
    }
}