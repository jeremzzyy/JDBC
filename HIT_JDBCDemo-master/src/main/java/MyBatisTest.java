import dao.IUserDao;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MyBatisTest {
    public static void main(String[] args) throws IOException {
        //读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory构造者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //使用构造者创建SqlSessionFactory
        SqlSessionFactory factory = builder.build(in);
        //使用SqlSessionFactory创建SqlSession
        SqlSession session = factory.openSession();
        //使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //使用代理对象执行查询所有方法
        List<User> users = userDao.findAll();
        for (User user:users){
            System.out.println(user);
        }
    }
}
