//package com.example.compound.api;
//
//import com.example.compound.entities.User;
//import com.example.compound.exceptions.UserAuthException;
//import com.example.compound.repositories.UserRepository;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class UserInteractor {
//
//
///**
// * This is a use case class that controls what users are permitted to do with the Student entity.
// * It is closely linked to the repository. We have to register this class with Spring Boot so it can be injected into the controller
// * - so we can tag it with @Configuration.
// */
//
//    private final UserRepository repository;
//
//    /**
//     * Defines a repository for this use case. The repository will be "injected" by Spring Boot when this class is
//     * instantiated as the web server starts up.
//     */
//    public UserInteractor(UserRepository repository) {
//        this.repository = repository;
//    }
//
//    /**
//     * Returns all Students in the database.
//     */
//    public List<User> getAllUsers(){
////        return repository.findAll();
//        return null;
//    }
//
//    /**
//     * Creates a student and saves it to the database.
//     */
//    public User createUser(List<String> details){
//        User user = new User(details.get(0), 0.00, details.get(2));
////        return repository.save(user);
//        return null;
//    }
//
//
////    public Student getStudentById(long id) throws StudentNotFound{
////        return repository.findById(id).orElseThrow(StudentNotFound::new);
////    }
////
////
////    public Student updateCourses(Student student, List<String> newCourses) {
////        student.setCourses(newCourses);
////        return repository.save(student);
////    }
//
//
//    /**
//     * Accepts a Student object and extracts all permissible information from it, updating the corresponding student
//     * in the database.
//     * @param id The ID of the student to update
//     * @param student the new student object, from which to extract data from
//     * @return the updated student object.
//     * @throws StudentNotFound  if the id does not match any student
//    */
////    public User updatePermittedFields ( long id, User user) throws UserAuthException {
////        User existingUser = this.getUserById(id);
////
////        //    Only permitted to update courses - we ignore any other changes to the student passed in
////        return updateCourses(existingUser, user.getCourses());
////    }
//}
