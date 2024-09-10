package com.example.demo.repo;
import com.example.demo.classes.PozycjaZamowienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PozycjaZamowienieRepository extends JpaRepository<PozycjaZamowienie, Integer> {


}
