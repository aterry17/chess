package model;

public record UserData(String username, String password, String email) {


    /// example of how you would implement a method to change username, password, or email
//     UserData newPassword(String newPassword) {
//         return new UserData((username), newPassword, email);
//     }
}
