/**
 * Mulla Book Store
 * 
 * Admin login module.
 * 
 * @author D. Kasi Pavan Kumar
 * @version 1.0
 * @date 21 October 2019
 */

package registration.admin;

public class AdminForm {
    private final String username = "Admin_MBS";
    private final String password = "Admin@MBS#657";

    public Boolean LoginAuth(String username, String password) {
        if(username.equals(this.username) && password.equals(this.password)) {
            return(true);
        }
        return(false);
    }
}