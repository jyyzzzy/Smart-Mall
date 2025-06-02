package com.smartmall.mapper;

import com.smartmall.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {

    /**
     * Queries all categories (basic information).
     *
     * @return A list of Category objects.
     */
    @Select("SELECT category_id, category_name, merchant_id FROM categories")
    List<Category> selectAll();

    /**
     * Queries categories by their parent's internal numeric ID (basic information).
     *
     * @param parentId The internal numeric ID (typically a BIGINT primary key) of the parent category.
     * @return A list of child Category objects.
     */
    @Select("SELECT category_id, category_name, merchant_id FROM categories WHERE parent_id = #{parentId}")
    List<Category> selectByParentId(@Param("parentId") Long parentId);

    /**
     * Inserts a new category.
     * Assumes 'id' is auto-generated and 'parent_id' is nullable or has a default if not provided.
     *
     * @param category The category object to insert. Only categoryId, categoryName, merchantId are used from this object.
     * @return The number of affected rows.
     */
    @Insert("INSERT INTO categories (category_id, category_name, merchant_id, parent_id) VALUES (#{categoryId}, #{categoryName}, #{merchantId}, NULL)")
    // If parent_id needs to be set, the Category domain object or method signature should include it.
    // Example with parentId if Category domain had it:
    // @Insert("INSERT INTO categories (category_id, category_name, merchant_id, parent_id) VALUES (#{categoryId}, #{categoryName}, #{merchantId}, #{parentId})")
    int insert(Category category);

    /**
     * Updates an existing category by its categoryId.
     *
     * @param category The category object with updated information.
     * @return The number of affected rows.
     */
    @Update("UPDATE categories SET category_name = #{categoryName}, merchant_id = #{merchantId} WHERE category_id = #{categoryId}")
    int updateById(Category category);

    /**
     * Retrieves all categories with their internal ID and parent ID for tree construction.
     *
     * @return A list of maps, where each map contains id, parent_id, category_id, category_name, merchant_id.
     */
    @Select("SELECT id, category_id, category_name, merchant_id, parent_id FROM categories")
    List<Map<String, Object>> findAllWithHierarchyData();
}