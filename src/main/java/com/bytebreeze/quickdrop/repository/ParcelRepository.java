package com.bytebreeze.quickdrop.repository;

import com.bytebreeze.quickdrop.enums.ParcelStatus;
import com.bytebreeze.quickdrop.model.Parcel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, UUID> {
	@Query("SELECT p FROM Parcel p WHERE p.sender.id = :userId AND p.status = :status")
	List<Parcel> findBySenderAndStatus(UUID userId, ParcelStatus status);

	boolean existsByTrackingId(String trackingId);
}
