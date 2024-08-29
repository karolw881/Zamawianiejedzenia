package com.example.demo.Repo;

import com.example.demo.Zamowienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZamowienieRepository extends JpaRepository<Zamowienie, Long> {

}
