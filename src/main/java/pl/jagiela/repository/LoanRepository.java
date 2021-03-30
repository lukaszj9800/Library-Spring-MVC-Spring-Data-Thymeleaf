package pl.jagiela.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jagiela.model.Loan;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
}
