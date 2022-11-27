package com.loveable.tracker.model;

import com.loveable.tracker.enums.Status;
import com.loveable.tracker.utils.UuidGenerator;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static com.loveable.tracker.enums.Status.IN_PROGRESS;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Task")
@Table(
        name = "task",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "task_unique_uuid",
                        columnNames = "uuid")
        }
)
public class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;
    @Column(
            name = "title",
            nullable = false
    )
    private String title;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Column(
            name = "status"
//            nullable = false
    )
    private Status status;
    @Column(
            name = "created_at",
//            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(
            name = "completed_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;
    @Column(
            name = "uuid",
            nullable = false
    )
    private String uuid;

    //    @Column(
//            name = "user_uuid",
//            nullable = false
//    )
//    private String userUuid;
    @PrePersist
    protected void prePersist() {
        System.out.println("inside prePersist");
        if (this.createdAt == null) createdAt = new java.util.Date();
        System.out.println("executed date");
        System.out.println(this.createdAt);
        if (this.status == null) status = IN_PROGRESS;
        this.uuid = UuidGenerator.uuid();
    }

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_task_fk"
            )
    )
    private User user;
}
