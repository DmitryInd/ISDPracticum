package ru.netology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    @Modifying
    @Transactional
    @Query("update File f set f.filename = :newFilename where f.filename = :originalFilename")
    void setFilenameById(@Param("newFilename") String newFilename,
                         @Param("originalFilename") String originalFilename);
}
