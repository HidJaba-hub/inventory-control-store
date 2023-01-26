package com.company.electro_store.server.functions;

import com.company.electro_store.service.accountant.SalariesService;
import com.company.electro_store.service.accountant.SalesService;
import com.company.electro_store.service.persons.PersonService;
import com.company.electro_store.service.persons.PostService;
import com.company.electro_store.service.persons.UserService;
import com.company.electro_store.service.persons.WorkService;
import com.company.electro_store.service.store.ProductService;
import com.company.electro_store.service.store.PropertyService;
import com.company.electro_store.service.store.RackService;
import com.company.electro_store.service.store.ShellService;

public abstract class Function{
    public static final PropertyService propertyService=new PropertyService();
    public static final RackService rackService=new RackService();
    public static final ShellService shellService=new ShellService();
    public static final ProductService productService=new ProductService();

    public static final SalariesService salariesService=new SalariesService();
    public static final SalesService salesService=new SalesService();
    public static final WorkService workService=new WorkService();
    public static final PostService postService=new PostService();
    public static final UserService userService=new UserService();
    public static final PersonService personService=new PersonService();

}
