package dao;

import domain.QueryVo;
import domain.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
    User findByID(Integer userID);
    int saveUser(User user);
    int updateUser(User user);
    int deleteUser(Integer userID);
    List<User> findByNameNew(String username);
    int count();
    List<User> findByVo(QueryVo queryVo);
}
