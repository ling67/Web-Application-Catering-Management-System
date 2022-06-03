package com.example.catering.controllers;

import com.example.catering.dto.MessageDetails;
import com.example.catering.exceptions.ResourceNotFoundException;
import com.example.catering.models.Menu;
import com.example.catering.repositories.MenuRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public Iterable<Menu> getMenus() {
        Iterable<Menu> menus = this.menuRepository.findAll();
        return menus;
    }

    @GetMapping("/{menuId}")
    public Menu getMenuById(@PathVariable Integer menuId) {
        Menu newMenu = menuRepository.findById(menuId).get();
        return newMenu;
    }

    @PostMapping
    public ResponseEntity<MessageDetails> addMenu(@RequestBody Menu menu) {
        menuRepository.save(menu);
        MessageDetails msg = new MessageDetails("The new menu was add successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @PutMapping
    public ResponseEntity<MessageDetails> updateMenu(@RequestBody Menu menu) {
        Menu newMenu = menuRepository.findById(menu.getId()).get();
        newMenu.setMenuName(menu.getMenuName());
        newMenu.setMenuType(menu.getMenuType());
        newMenu.setPrice(menu.getPrice());
        newMenu.setDescription(menu.getDescription());
        newMenu.setStatus(menu.getStatus());
        menuRepository.save(newMenu);
        MessageDetails msg = new MessageDetails("The new menu was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    // sold out
    @PutMapping("/status")
    public ResponseEntity<MessageDetails> updateStatus(@RequestParam Integer menuId, @RequestParam Integer status) {
        Menu menu = new Menu();
        try {
            menu = menuRepository.findById(menuId).get();   //为空有问题
        } catch (Exception e) {
            throw new ResourceNotFoundException("The menu id not exist. MenuId = " + menuId);
        }
        menu.setStatus(status);
        menuRepository.save(menu);
        MessageDetails msg = new MessageDetails("The menu status updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping
    public ResponseEntity<MessageDetails> removeEmployee(@RequestParam Integer menuId) {
        Menu menu = this.menuRepository.findById(menuId).get();
        if (menu != null) {
            this.menuRepository.deleteById(menuId);
            MessageDetails sucMsg = new MessageDetails("The menu was removed successfully.", true);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(sucMsg);
        } else {
            MessageDetails failMsg = new MessageDetails("Could not be found. menuId = " + menuId);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(failMsg);
        }
    }

}