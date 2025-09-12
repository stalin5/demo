package com.example.demo;

import com.example.demo.Item;
import com.example.demo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/item")
@CrossOrigin("*")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/{Id}")
    public Item getItem(@PathVariable Long Id) {
        return itemRepository.getItemsById(Id);
    }

    @GetMapping()
    public Page<Item> getItem(@PathVariable(required = false) String productName,
                              @PathVariable(required = false) String description,
                              @RequestParam(defaultValue = "0") int page,//page
                              @RequestParam(defaultValue = "10") int size,//page
                              @RequestParam(defaultValue = "productName") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return itemRepository.findByProductNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
                productName != null ? productName : "",
                description != null ? description : "",
                pageable
        );
    }

    @DeleteMapping("/{Id}")
    public void deleteItem(@PathVariable Long Id) {
        itemRepository.deleteById(Id);
    }

    @PostMapping()
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping()
    public Item updateItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }


}
