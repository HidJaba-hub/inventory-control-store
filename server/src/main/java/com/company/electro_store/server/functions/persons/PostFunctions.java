package com.company.electro_store.server.functions.persons;

import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.server.functions.Function;

import java.util.List;

public class PostFunctions extends Function {
    public static boolean saveOrUpdatePost(Post post) {
        List<Work> works=post.getActions();
        post.getActions().clear();
        for(Work work:works){
            work.setPost(post);
            workService.saveOrUpdate(work);
        }
        return postService.saveOrUpdate(post);
    }
    public static boolean deleteWork(Work work){
        return workService.delete(work);
    }
    public static boolean addAction(Work work){
        return workService.saveOrUpdate(work);
    }
    public static boolean deletePost(Post post){
        return postService.delete(post);
    }
    public static List<Post> getPosts(){
        return postService.readAll();
    }
    public static List<Work> getWorks(){
        return workService.readAll();
    }
    public static Post getPostByName(Integer name){
        return postService.read(name);
    }
}
