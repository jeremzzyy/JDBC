import dao.IUserDao;
import domain.QueryVo;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class userDAOTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IUserDao userDao;

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
        userDao = session.getMapper(IUserDao.class);
    }



    @Test
    public void testFindOne(){
        Assert.assertEquals("张三",userDao.findByID(41).getUserName());
    }

    @After
    public void tearDown() throws Exception {
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setUserName("华泰证券");
        user.setUserAddress("南京市建邺区江东中路");
        user.setUserSex("男");
        user.setUserBirthday(new Date());

        int affectRows = userDao.saveUser(user);
        Assert.assertEquals(1,affectRows);
//        Assert.assertEquals(63,(int)user.getId());
    }

    @Test
    public void testUpdate(){
        int id = 46;
        User user = userDao.findByID(id);
        user.setUserAddress("北京市顺义区");
        int res = userDao.updateUser(user);

        User savedUser = userDao.findByID(id);

        Assert.assertEquals("北京市顺义区", savedUser.getUserAddress());
    }

    @Test
    public void testDeleteUser() {
        int res = userDao.deleteUser(67);
        assert res == 1;
    }

    @Test
    public void testFindByNameNew(){
        List<User> users = userDao.findByNameNew("王");
        assert users.size() == 2;

        for (User user:users){
            System.out.println(user);
        }
    }

    @Test
    public void testCount() {
        int res = userDao.count();
        assert res == 9;
    }

    @Test
    public void testFindByVo() {
        QueryVo vo = new QueryVo();
        vo.setUsername("%王%");
        vo.setAddress("%南京%");

        List<User> users = userDao.findByVo(vo);

        assert users.size() == 1;
    }

    @Test
    public void testQueryByVo(){
        QueryVo vo = new QueryVo();
        vo.setUsername("%王%");
        vo.setAddress(null);
        List<User> users = userDao.findByVo(vo);

        assert users.size() == 1;
    }

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        assert users.size() == 22;

        for (User user:users
        ) {
            System.out.println(user);
        }
    }

}
