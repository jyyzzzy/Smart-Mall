package com.smartmall.service.impl;

import com.smartmall.domain.Category;
import com.smartmall.mapper.CategoryMapper;
import com.smartmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int addCategory(Category category) {
        // Additional validation (e.g., check if categoryId already exists) can be added here.
        return categoryMapper.insert(category);
    }

    @Override
    public int updateCategory(Category category) {
        // Additional validation (e.g., check if category exists) can be added here.
        return categoryMapper.updateById(category);
    }

    @Override
    public List<Map<String, Object>> getCategoryTree() {
        List<Map<String, Object>> allCategoriesRaw = categoryMapper.findAllWithHierarchyData();
        Map<Long, Map<String, Object>> categoryMap = new HashMap<>();
        List<Map<String, Object>> rootCategories = new ArrayList<>();

        // Prepare map and identify root categories
        for (Map<String, Object> catRaw : allCategoriesRaw) {
            // Ensure 'children' list exists
            catRaw.putIfAbsent("children", new ArrayList<>());
            categoryMap.put((Long) catRaw.get("id"), catRaw);

            Object parentIdObj = catRaw.get("parent_id");
            if (parentIdObj == null) {
                rootCategories.add(catRaw);
            }
        }

        // Build the tree structure
        for (Map<String, Object> catRaw : allCategoriesRaw) {
            Object parentIdObj = catRaw.get("parent_id");
            if (parentIdObj != null) {
                Long parentId = ((Number) parentIdObj).longValue(); // Handle various numeric types from DB
                Map<String, Object> parentCategory = categoryMap.get(parentId);
                if (parentCategory != null) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> children = (List<Map<String, Object>>) parentCategory.get("children");
                    children.add(catRaw);
                }
                // Else: orphan category, or parent_id points to a non-existent category.
                // Depending on requirements, this could be logged or handled.
            }
        }
        return rootCategories;
    }

    @Override
    public List<Category> listCategories() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> getCategoriesByParentId(Long parentId) {
        return categoryMapper.selectByParentId(parentId);
    }
}