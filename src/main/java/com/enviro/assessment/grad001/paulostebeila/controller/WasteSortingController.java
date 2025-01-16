package com.enviro.assessment.grad001.paulostebeila.controller;

import com.enviro.assessment.grad001.paulostebeila.model.WasteCategory;
import com.enviro.assessment.grad001.paulostebeila.repository.WasteCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-categories")
public class WasteSortingController {

    private final WasteCategoryRepository wasteCategoryRepository;

    @Autowired
    public WasteSortingController(WasteCategoryRepository wasteCategoryRepository) {
        this.wasteCategoryRepository = wasteCategoryRepository;
    }

    // GET all waste categories
    @GetMapping
    public List<WasteCategory> getAllWasteCategories() {
        return wasteCategoryRepository.findAll();
    }

    // POST to create a new waste category
    @PostMapping
    public ResponseEntity<WasteCategory> createWasteCategory(@RequestBody WasteCategory wasteCategory) {
        WasteCategory savedCategory = wasteCategoryRepository.save(wasteCategory);
        return ResponseEntity.ok(savedCategory);
    }

    // PUT to update a waste category
    @PutMapping("/{id}")
    public ResponseEntity<WasteCategory> updateWasteCategory(
            @PathVariable Long id,
            @RequestBody WasteCategory wasteCategoryDetails) {
        return wasteCategoryRepository.findById(id)
                .map(category -> {
                    category.setName(wasteCategoryDetails.getName());
                    WasteCategory updatedCategory = wasteCategoryRepository.save(category);
                    return ResponseEntity.ok(updatedCategory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE to remove a waste category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteCategory(@PathVariable Long id) {
        return wasteCategoryRepository.findById(id)
                .map(category -> {
                    wasteCategoryRepository.delete(category);
                    return ResponseEntity.ok().<Void>build(); // Explicitly specifying Void
                })
                .orElseGet(() -> ResponseEntity.notFound().<Void>build()); // Explicitly specifying Void
    }

}
