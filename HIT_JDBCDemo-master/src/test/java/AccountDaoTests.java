import dao.IAccountDao;
import dao.IUserDao;
import domain.AccountUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AccountDaoTests {
    private IAccountDao accountDao ;

    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;


    @Before
    public void setUp() throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlSessionFactory构造者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //使用构造者创建SqlSessionFactory
        factory = builder.build(in);
        //使用SqlSessionFactory创建SqlSession
        session = factory.openSession();
        //使用SqlSession创建Dao接口的代理对象
        accountDao = session.getMapper(IAccountDao.class);
    }

    @After
    public void tearDown() throws Exception {
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<AccountUser> accountUsers = accountDao.findAll();
        for (AccountUser accountUser: accountUsers){
            System.out.println(accountUser);
        }

        assert accountUsers.size()==3;
    }
}