package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.ImportedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportedFileRepository extends JpaRepository<ImportedFile, Long> {
}
