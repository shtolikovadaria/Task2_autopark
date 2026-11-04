package com.example.autopark.repository;

import com.example.autopark.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    // Поиск по марке, ФИО и году. Пустой запрос возвращает все записи.
    @Query("SELECT c FROM Car c WHERE " +
            "(:q = '' OR LOWER(c.markaAvto) LIKE LOWER(CONCAT('%', :q, '%')) " +
            "OR LOWER(c.ownerName) LIKE LOWER(CONCAT('%', :q, '%')) " +
            "OR CAST(c.yearOfIssue AS string) LIKE CONCAT('%', :q, '%')) " +
            "ORDER BY c.id")
    List<Car> search(@Param("q") String q);

    // Вариант У–Я: сортировка по году выпуска, пустые марки игнорируются.
    @Query("SELECT c FROM Car c WHERE TRIM(c.markaAvto) <> '' AND " +
            "(:q = '' OR LOWER(c.markaAvto) LIKE LOWER(CONCAT('%', :q, '%')) " +
            "OR LOWER(c.ownerName) LIKE LOWER(CONCAT('%', :q, '%')) " +
            "OR CAST(c.yearOfIssue AS string) LIKE CONCAT('%', :q, '%')) " +
            "ORDER BY c.yearOfIssue ASC, c.id ASC")
    List<Car> searchSortedByYear(@Param("q") String q);
}