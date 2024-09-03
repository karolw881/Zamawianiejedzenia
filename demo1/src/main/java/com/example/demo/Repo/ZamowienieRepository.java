package com.example.demo.Repo;
import com.example.demo.Zamowienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZamowienieRepository extends JpaRepository<Zamowienie, Long> {
    @Query("SELECT z FROM Zamowienie z ORDER BY z.id DESC Limit 1 ")
    Zamowienie findLastZamowienie();
}
