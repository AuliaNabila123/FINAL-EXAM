/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws.b.Finalexam;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.core.convert.TypeDescriptor.map;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aulia Nabila Realisti (20200140006)
 */

@RestController
@CrossOrigin
@ResponseBody

public class myController {
    Person data = new Person();
    PersonJpaController ctrl = new PersonJpaController();
    
    //Membuat fungsi get dengan id
    @RequestMapping(value = "/GET/{id}")
    public String getDataById(@PathVariable("id") int id){
        try{
            data = ctrl.findPerson(id);
        } catch (Exception e){
            
        }
        //hasil dari get by id dengan menampilkan nama dan nik
        return data.getNama()+ " dengan NIK : " + data.getNik().toString();
    }
    
    //Membuat fungsi get dengan menampilkan semua data
    @RequestMapping(value = "/GET")
    public List<Person> getAll() { 
        List<Person> person = new ArrayList();
        try {
            person = ctrl.findPersonEntities();
        } catch (Exception e) {
          person = List.of();
        }
        return person;
    }
    
    //Membuat fungsi post atau create data
    @RequestMapping(value = "/POST", 
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE)
    public String createData(HttpEntity<String> paket) {
        String message = "";
        try {
            String json_receive = paket.getBody();
            ObjectMapper map = new ObjectMapper();
            Person data = new Person();
            data = map.readValue(json_receive, Person.class);
            ctrl.create(data);
            //message jika data berhasil ditambahkan
            message = data.getNama()+ " Data Seved";
        } catch(Exception e) {
            message = " Filed";
        }
        return message;
    }
    
    //Membuat fungsi put untuk mengedit data
    @RequestMapping(value = "/PUT",
            method = RequestMethod.PUT,
            consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> kiriman) {
        String message = "Filed Edit";
        try{
            String json_receive = kiriman.getBody();
            ObjectMapper map = new ObjectMapper();
            Person newdatas = new Person();
            newdatas = map.readValue(json_receive, Person.class);
            ctrl.edit(newdatas);
            //message jika data berhasil di edit
            message = newdatas.getNama()+ " has been edit";
            
        } catch (Exception e){ 
        }
        return message;
    }
    
    //Membuat fungsi delete 
    @RequestMapping(value ="/DELETE",
            method = RequestMethod.DELETE,
            consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> kiriman) {
        String message = "Id tidak ada";
        try {
            String json_receive = kiriman.getBody();
            ObjectMapper map = new ObjectMapper();
            Person newdatas = new Person();
            newdatas = map.readValue(json_receive, Person.class);
            ctrl.destroy(newdatas.getId());
            //message jika data berhasil di delete
            message = newdatas.getNama()+ " has been deleted";
        } catch (Exception e){}
        return message;
    }
}

