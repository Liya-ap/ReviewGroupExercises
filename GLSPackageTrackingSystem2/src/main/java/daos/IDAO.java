package daos;

import java.util.Set;

public interface IDAO <T> {
    T getById(Long id);
    T getByTrackingNumber(String trackingNumber);
    Set<T> getAll();
    void create(T t);
    void updateStatus(T t);
    void deletePackage(T t);
}
