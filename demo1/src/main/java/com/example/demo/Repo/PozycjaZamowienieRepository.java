package com.example.demo.Repo;

import com.example.demo.pozycjaZamowienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PozycjaZamowienieRepository extends JpaRepository<pozycjaZamowienie, Integer> {

}
