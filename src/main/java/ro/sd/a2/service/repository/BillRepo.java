package ro.sd.a2.service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Bill;

import java.util.Optional;

@Repository
public interface BillRepo extends JpaRepository<Bill,String> {
    @Query( value = "SELECT * FROM Bill t WHERE t.id = ?1", nativeQuery = true)
    Bill findByUuid(String id);
}
