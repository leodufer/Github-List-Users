package py.edu.facitec.githubusers;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UserService {

    @GET("/users")
    void getUsers(Callback<List<User>> callback);

    @GET("/users/{login}")
    void getUserByLogin(@Path("login") String login, Callback<User> callback);
}
