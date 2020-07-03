package by.bntu.smartlibrary;

import by.bntu.smartlibrary.dao.UserDaoImpl;
import by.bntu.smartlibrary.domain.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/users")
public class UserController {

    UserDaoImpl userDao;

    @Autowired
    public UserController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @ApiOperation(value = "Finding all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading users"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        Optional<List<User>> all = userDao.findAll();
        return new ResponseEntity<>(all.orElseThrow(), HttpStatus.OK);
    }

    @ApiOperation(value = "Update User")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        Optional<User> updatedUser = userDao.update(user);
        return new ResponseEntity<>(updatedUser.orElseThrow(), HttpStatus.OK);
    }

    @ApiOperation(value = "Finding user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading user"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User database id", example = "7", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByName(@PathVariable("id") Long id) {
        Optional<User> user = userDao.findById(id);
        return new ResponseEntity<>(user.orElseThrow(), HttpStatus.OK);

    }
    @ApiOperation(value = "Create user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful creation user"),
            @ApiResponse(code = 422, message = "Failed user creation properties validation"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Optional<User> savedUser = userDao.save(user);
        return new ResponseEntity<>(savedUser.orElseThrow(), HttpStatus.OK);
    }

}
