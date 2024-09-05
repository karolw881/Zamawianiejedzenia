package com.example.demo.Repo;
import com.example.demo.PozycjaZamowienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PozycjaZamowienieRepository extends JpaRepository<PozycjaZamowienie, Integer> {

    @Query(value = "SELECT z FROM PozycjaZamowienie z ORDER BY z.id DESC Limit 1")
    PozycjaZamowienie findLastPozycjaZamowienie();
}
