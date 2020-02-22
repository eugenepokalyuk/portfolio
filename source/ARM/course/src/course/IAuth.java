package course;

public interface IAuth {
    void login(String url, String username, String password) throws Exception;
    void logout() throws Exception; // close on login
}
