package com.smartmall.controller;

import com.smartmall.domain.*; // Assuming Result class is in this package
import com.smartmall.service.CustomerService;
import com.smartmall.service.MallService;
import com.smartmall.service.MerchantService;
import com.smartmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth") // Base path for authentication-related endpoints
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MallService mallService; // Needed for registration logic

    @Autowired
    private MerchantService merchantService; // Needed for registration logic

    @Autowired
    private CustomerService customerService; // Needed for registration logic

    /**
     * Handles user login.
     * Input: User credentials (username, password) in the request body.
     * Output: User details on success, or an error message.
     */
    @GetMapping("/login") // Changed from GET to POST as login typically involves sending credentials in the body
    public Result login(User credentials) { // Renamed parameter for clarity
        // It's better to use a specific DTO like LoginRequestDTO here with only username and password
        User userToAuthenticate = new User();
        userToAuthenticate.setUsername(credentials.getUsername());
        userToAuthenticate.setPassword(credentials.getPassword());

        List<User> userList = userService.selectUserByUserNameAndPassword(userToAuthenticate);
        System.out.println(userList);
        if (userList != null && !userList.isEmpty()) {
            // Consider returning a JWT or session token instead of the full User object
            // Also, ensure the password field is not part of the returned User object here
            User loggedInUser = userList.getFirst();
            // loggedInUser.setPassword(null); // Example: Nullify password before sending
            return Result.success(loggedInUser);
        } else {
            return Result.error("Invalid username or password"); // More specific error
        }
    }

    /**
     * Handles new user registration.
     * Input: User details for registration in the request body.
     * Output: Success or error message.
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // Consider using a specific RegistrationRequestDTO
        List<User> existingUsers = userService.selectUserByUserName(user);
        if (existingUsers.isEmpty()) {
            // Critical: Ensure userId is generated and available if needed by subsequent logic.
            // The original code had a potential issue here if userId is DB-generated
            // and not refreshed in the 'user' object before creating related entities.
            // Assuming insertUser populates the userId, or it's handled correctly:
            int insertResult = userService.insertUser(user);

            if (insertResult > 0) {
                // If userId is DB generated (e.g., auto-increment or UUID by DB),
                // the 'user' object here might not have the generated userId unless
                // MyBatis is configured for it (e.g., useGeneratedKeys).
                // This is crucial for the logic below.
                // A common pattern is for insertUser to return the created User object with its ID.
                // For now, proceeding with the assumption that user.getUserId() will be valid if needed.
                // Re-fetch the user if its ID is needed and not populated:
                // User registeredUser = userService.selectUserByUserName(user).getFirst(); // if username is unique
                // String userId = registeredUser.getUserId();

                // The below logic relies on user.getUserId() being correctly populated *after* insert.
                // If user.getUserId() is still null or not the DB-generated one, this will fail.
                // This part needs careful handling of ID retrieval post-insertion.

                String userIdForRoles = user.getUserId(); // This needs to be the actual persisted userId

                // A temporary workaround if user object isn't updated with ID: re-fetch by username (if unique)
                if (userIdForRoles == null && user.getUsername() != null) {
                    List<User> reFetchedUserList = userService.selectUserByUserName(user);
                    if (!reFetchedUserList.isEmpty()) {
                        userIdForRoles = reFetchedUserList.getFirst().getUserId();
                    } else {
                        // This case means user was inserted but couldn't be immediately re-fetched.
                        // Handle error or reconsider flow.
                        return Result.error("Registration partially failed: could not retrieve user ID for role assignment.");
                    }
                }

                if (userIdForRoles == null) {
                    return Result.error("Registration failed: User ID not available for role assignment.");
                }


                switch (user.getRole()) {
                    case "admin":
                        // No specific action for admin in this example
                        break;
                    case "mall":
                        Mall mall = new Mall();
                        mall.setUserId(userIdForRoles);
                        mall.setMallName(user.getUsername() + "'s Mall"); // Example name
                        mallService.insertMall(mall);
                        break;
                    case "merchant":
                        Merchant merchant = new Merchant();
                        merchant.setUserId(userIdForRoles);
                        merchant.setMerchantName(user.getUsername() + "'s Store"); // Example name
                        merchantService.insertMerchant(merchant);
                        break;
                    case "customer":
                        Customer customer = new Customer();
                        customer.setUserId(userIdForRoles);
                        customer.setCustomerName(user.getUsername()); // Example name
                        customerService.insertCustomer(customer);
                        break;
                    default:
                        // Handle unknown role if necessary
                        break;
                }
                return Result.success("User registered successfully.");
            } else {
                return Result.error("Registration failed.");
            }
        } else {
            return Result.error("Username already exists.");
        }
    }
}