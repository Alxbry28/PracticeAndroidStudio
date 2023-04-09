package com.example.practice.interfaces;

import java.util.List;

public interface iRepository<Object> {

     //Implement this method to a certain desired class
     // iInterfaceName<Generic>

     /* Explaination
     * Implement these method to PersonRepository
     * Para hindi na mag repeat ng pag tatype ng
     * method na ito sa different repository class
     * */
     boolean Delete();
     boolean Create();
     boolean Update();
     Object GetById();
     List<Object> GetAll();


}
