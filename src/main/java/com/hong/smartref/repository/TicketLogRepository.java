package com.hong.smartref.repository;

import com.hong.smartref.data.entity.TicketLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketLogRepository  extends JpaRepository<TicketLog, Integer> {

}
