package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis Jedis = JedisUtil.getJedis();
        Set<String> categorys = Jedis.zrange("category", 0, -1);
        List<Category> cs = null;

        if (categorys == null || categorys.size() == 0){
            cs = categoryDao.findAll();

            for (int i = 0;i < cs.size(); i++){
                Jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());

            }
        }else{
            cs = new ArrayList<Category>();
            for (String name: categorys){
                Category category = new Category();
                category.setCname(name);
                cs.add(category);
            }
        }

        return cs;
    }
}
