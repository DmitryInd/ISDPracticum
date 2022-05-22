package ru.netology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.entity.user.AuthorizationInfo;
import ru.netology.entity.user.Role;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findFirstByAuthorizationInfo(AuthorizationInfo authorizationInfo);
}
