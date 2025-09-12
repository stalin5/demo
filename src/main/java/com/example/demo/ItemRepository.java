package com.example.demo;

import jakarta.transaction.Transactional;
import com.example.demo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {


    Page<Item> findByProductNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String s, String s1, Pageable pageable) ;

    Item getItemsById(Long id);

    List<Item> findByDescription(String s, Pageable pageable);
}
