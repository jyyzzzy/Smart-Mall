package com.smartmall.controller;

import com.smartmall.domain.Category;
import com.smartmall.domain.Result; // Assuming Result class is in com.smartmall.domain
import com.smartmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // For method-level security
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Adds a new product category.
     * Requires ADMIN or MERCHANT role (example security).
     *
     * @param category The category data from the request body.
     * @return Result object indicating success or failure.
     */
    // @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @PostMapping
    public Result addCategory(@RequestBody Category category) {
        // Consider using a DTO for creation to control incoming fields
        // Add validation for category data
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return Result.error("Category name cannot be empty.");
        }
        if (category.getCategoryId() == null || category.getCategoryId().trim().isEmpty()) {
            return Result.error("Category ID cannot be empty.");
        }
        // Potentially check for duplicate categoryId before insertion

        int result = categoryService.addCategory(category);
        if (result > 0) {
            return Result.success("Category added successfully.");
        } else {
            return Result.error("Failed to add category.");
        }
    }

    /**
     * Retrieves product categories.
     * Can return a flat list or a hierarchical tree structure based on the 'view' parameter.
     * Publicly accessible.
     *
     * @param view Optional. If 'tree', returns a category tree. Otherwise, returns a flat list.
     * @return Result object containing the list of categories or category tree.
     */
    @GetMapping
    public Result getCategories(@RequestParam(required = false) String view) {
        if ("tree".equalsIgnoreCase(view)) {
            List<Map<String, Object>> categoryTree = categoryService.getCategoryTree();
            return Result.success(categoryTree);
        } else {
            List<Category> categoryList = categoryService.listCategories();
            return Result.success(categoryList);
        }
    }

    /**
     * Retrieves sub-categories for a given parent ID.
     * Publicly accessible.
     *
     * @param parentId The internal numeric ID of the parent category.
     * @return Result object containing the list of child categories.
     */
    @GetMapping("/by-parent/{parentId}")
    public Result getCategoriesByParentId(@PathVariable Long parentId) {
        List<Category> categories = categoryService.getCategoriesByParentId(parentId);
        return Result.success(categories);
    }


    /**
     * Modifies an existing product category.
     * Requires ADMIN or MERCHANT role (example security).
     *
     * @param categoryId The ID of the category to modify.
     * @param category   The updated category data from the request body.
     * @return Result object indicating success or failure.
     */
    // @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @PutMapping("/{categoryId}")
    public Result updateCategory(@PathVariable String categoryId, @RequestBody Category category) {
        // Ensure the categoryId from the path is used, not from the body if it differs.
        category.setCategoryId(categoryId);
        // Add validation for category data
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            return Result.error("Category name cannot be empty.");
        }

        int result = categoryService.updateCategory(category);
        if (result > 0) {
            return Result.success("Category updated successfully.");
        } else {
            // Could be due to category not found or other update failure
            return Result.error("Failed to update category or category not found.");
        }
    }

    // Note: DELETE operation for categories was not specified, but would typically be:
    /*
    // @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    @DeleteMapping("/{categoryId}")
    public Result deleteCategory(@PathVariable String categoryId) {
        // Implementation for deleting a category
        // Consider handling child categories (e.g., disallow delete if children exist, or cascade delete)
        // int result = categoryService.deleteCategory(categoryId);
        // if (result > 0) {
        //     return Result.success("Category deleted successfully.");
        // } else {
        //     return Result.error("Failed to delete category or category not found.");
        // }
        return Result.error("Delete operation not yet implemented.");
    }
    */
}