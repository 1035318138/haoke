package cn.itcast.haoke.dubbo.api.controller;

import cn.itcast.haoke.dubbo.api.service.HouseResourcesService;
import cn.itcast.haoke.dubbo.server.pojo.HouseResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("house/resources")
public class HouseResourcesController {

    @Autowired
    private HouseResourcesService houseResourcesService;


    @PostMapping
    @ResponseBody
   public ResponseEntity<Void> save(@RequestBody HouseResources houseResources){

       boolean save = houseResourcesService.save(houseResources);
       if(save){
           return ResponseEntity.status(HttpStatus.CREATED).build();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

   }

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> get(){
       return ResponseEntity.ok("ok");
   }
}
