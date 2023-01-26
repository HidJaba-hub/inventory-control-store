package com.company.electro_store.service.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.persons.PostDao;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.service.Service;

import java.util.List;

public class PostService implements Service<Post> {
    Dao<Post> postDao = new PostDao();

    @Override
    public boolean saveOrUpdate(Post post) {
        return postDao.saveOrUpdate(post);
    }

    @Override
    public List<Post> readAll() {
        return postDao.readAll();
    }
    @Override
    public boolean delete(Post post) {
        return postDao.delete(post);
    }
    @Override
    public Post read(Integer id) {
        return postDao.read(id);
    }
    @Override
    public Post read(String id) {
        return postDao.read(id);
    }
}
