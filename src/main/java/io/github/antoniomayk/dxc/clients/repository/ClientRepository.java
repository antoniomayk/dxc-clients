package io.github.antoniomayk.dxc.clients.repository;

import io.github.antoniomayk.dxc.clients.entity.Client;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing {@link Client} entities.
 *
 * @author Antonio Mayk
 * @since 0.1
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL")
  Collection<Client> findAllActiveClients();

  @Modifying
  @Transactional
  @Query(
      value = "UPDATE clients SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?1",
      nativeQuery = true)
  void deactivateClient(Long clientId);

  @Query(
      value = "SELECT EXISTS (SELECT 1 FROM clients WHERE id =?1 AND deleted_at IS NULL)",
      nativeQuery = true)
  boolean existsByIdAndNotDeleted(Long clientId);
}
