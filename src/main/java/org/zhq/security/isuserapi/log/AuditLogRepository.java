package org.zhq.security.isuserapi.log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zhq.security.isuserapi.user.User;


public interface AuditLogRepository extends JpaSpecificationExecutor<AuditLog>, CrudRepository<AuditLog,Long> {
}
