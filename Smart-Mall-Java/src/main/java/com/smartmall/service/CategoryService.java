package com.smartmall.service;

import com.smartmall.domain.Category;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    /**
     * Adds a new category.
     *
     * @param category The category to add.
     * @return The number of affected rows.
     */
    int addCategory(Category category);

    /**
     * Updates an existing category.
     *
     * @param category The category to update.
     * @return The number of affected rows.
     */
    int updateCategory(Category category);

    /**
     * Retrieves the category tree structure.
     * Each element in the list is a map representing a root category,
     * potentially containing a 'children' key with a list of child category maps.
     *
     * @return A list of maps representing the category tree.
     */
    List<Map<String, Object>> getCategoryTree();

    /**
     * Retrieves a flat list of all categories.
     *
     * @return A list of categories.
     */
    List<Category> listCategories();

    /**
     * Retrieves categories by parent ID.
     * Note: This returns List<Category> which by default only contains categoryId, categoryName, merchantId.
     * The parentId parameter refers to the internal numeric ID of the parent.
     *
     * @param parentId The internal numeric ID of the parent category.
     * @return A list of child categories.
     */
    List<Category> getCategoriesByParentId(Long parentId);
}