package org.example.mainpriject.repository;

import org.example.mainpriject.model.Task;
import org.example.mainpriject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwner(User owner);
    List<Task> findByOwnerEmail(String email);
    Page<Task> findByOwner(User owner, Pageable pageable);
    Page<Task> findAll(Pageable pageable);
}
