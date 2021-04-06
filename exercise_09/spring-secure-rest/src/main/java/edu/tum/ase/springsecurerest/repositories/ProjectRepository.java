package edu.tum.ase.springsecurerest.repositories;

import edu.tum.ase.springsecurerest.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
