package edu.tum.ase.backendservice.repositories;

import edu.tum.ase.backendservice.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
