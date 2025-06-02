package com.smartmall.controller;

import com.smartmall.domain.User; // Assuming Result class is in com.smartmall.domain
import com.smartmall.domain.Result; // Explicit import for Result
import com.smartmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // Base path for user management
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves a list of all users.
     * (Corresponds to original /find-all)
     */
    @GetMapping("/find-all")
    public Result getAllUsers() {
        List<User> userList = userService.findAll();
        // Consider using a DTO that excludes sensitive information like passwords
        return Result.success(userList);
    }

    /**
     * Searches for users based on provided criteria in the User object (mapped from query parameters).
     * (Corresponds to original GET /user)
     * Example: /users/search?username=john&role=customer
     */
    @GetMapping("/{userId}")
    public Result getUserById(@PathVariable String userId) { // Spring populates 'filter' from query parameters
        // The User object will have fields set if they are present in the query string.
        // Be mindful of the UserMapper.selectUserList query which uses OR and includes password.
        // Searching by password in GET query parameters is highly discouraged.
        System.out.println(userId);
        User user =new User();
        user.setUserId(userId);
        // Consider using a DTO
        return Result.success(userService.selectUserByUserId(user));
    }

    /**
     * Creates a new user. This might be an admin-only function.
     * (Corresponds to original POST /user)
     * For user self-registration, use /auth/register.
     */
    @PostMapping
    public Result createUser(@RequestBody User user) {
        // Consider using a UserCreationRequestDTO
        // Add validation if user already exists, etc., if not handled by DB constraints
        int result = userService.insertUser(user);
        if (result > 0) {
            // Optionally return the created user (without sensitive data)
            return Result.success("User created successfully.");
        } else {
            return Result.error("Failed to create user.");
        }
    }

    /**
     * Updates an existing user's information.
     * (Corresponds to original PUT /user)
     */
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable String userId, @RequestBody User user) {
        // Consider using a UserUpdateRequestDTO
        // Ensure the client cannot update certain fields (e.g., role) unless authorized.
        // It's good practice to set the ID from the path variable to avoid mismatches.
        user.setUserId(userId);
        int result = userService.updateUser(user);
        if (result > 0) {
            return Result.success("User updated successfully.");
        } else {
            return Result.error("Failed to update user or user not found.");
        }
    }

    /**
     * Deletes a user by their ID.
     * (Corresponds to originally DELETE /user)
     */
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable String userId) {
        int result = userService.deleteUserByUserId(userId);
        if (result > 0) {
            return Result.success("User deleted successfully.");
        } else {
            return Result.error("Failed to delete user or user not found.");
        }
    }
}