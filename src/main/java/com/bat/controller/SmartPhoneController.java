package com.bat.controller;

import com.bat.model.SmartPhone;
import com.bat.service.ISmartPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/smartphones")
public class SmartPhoneController {
    @Autowired
    private ISmartPhoneService smartPhoneService;

    @PostMapping
    public ResponseEntity<SmartPhone> createSmartphone(@RequestBody SmartPhone smartPhone){
        return new ResponseEntity<>(smartPhoneService.save(smartPhone), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ModelAndView getAllSmartphonePage(){
        ModelAndView modelAndView = new ModelAndView("/phones/list");
        modelAndView.addObject("smartphones",smartPhoneService.findAll());
        return modelAndView;
    }
    @GetMapping
    public ResponseEntity<Iterable<SmartPhone>> showAllPhones(){
        return new ResponseEntity<>(smartPhoneService.findAll(),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<SmartPhone> deleteSmartphone(@PathVariable Long id){
        Optional<SmartPhone> smartPhoneOptional = smartPhoneService.findById(id);
        if (!smartPhoneOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartPhoneService.remove(id);
        return new ResponseEntity<>(smartPhoneOptional.get(),HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SmartPhone> updateSmartphone(@PathVariable Long id,@RequestBody SmartPhone smartPhone){
        Optional<SmartPhone> smartPhoneOptional = smartPhoneService.findById(id);
        smartPhone.setId(smartPhoneOptional.get().getId());
        if (!smartPhoneOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        smartPhoneService.save(smartPhone);
        return new ResponseEntity<>(smartPhone,HttpStatus.ACCEPTED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SmartPhone> findOne(@PathVariable Long id){
        return new ResponseEntity<>(smartPhoneService.findById(id).get(),HttpStatus.OK);
    }
}
