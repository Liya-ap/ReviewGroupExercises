package entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tracking_number", unique = true, nullable = false)
    private String trackingNumber;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public enum DeliveryStatus {
        PENDING,
        IN_TRANSIT,
        DELIVERED
    }

    @PrePersist
    private void prePersist() {
        this.timestamp = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.timestamp = LocalDateTime.now();
    }
}