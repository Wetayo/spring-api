package wetayo.wetayoapi.common;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class CommonColumns {
    @CreationTimestamp
    @Column(name = "CREATED_TIME", insertable = false)
    private LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(name = "UPDATED_TIME", updatable = false, insertable = false)
    private LocalDateTime updatedTime;
}
