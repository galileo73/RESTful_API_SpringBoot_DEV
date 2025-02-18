package com.ired.prototype.restfulAPI.repository;

import com.ired.prototype.restfulAPI.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandExecutionRepository extends JpaRepository<CommandExecution, Long> {
}