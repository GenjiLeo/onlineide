package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.SourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Note that Spring provides a variety of Repository abstractions, JpaRepository is
 * technology-specific
 * see https://docs.spring.io/spring-data/jdbc/docs/1.0.11.RELEASE/reference/html/#
 * repositories.core-concepts
 */
@Repository
public interface SourceFileRepository extends JpaRepository<SourceFile, String> {
    List<SourceFile> findByName(String name);
}