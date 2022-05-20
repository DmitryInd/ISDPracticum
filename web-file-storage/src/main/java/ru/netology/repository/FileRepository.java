package ru.netology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, String> {}
