package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	
	@Autowired
    private PlayerRepository playerRepository;

    public InputForm saveUserInput(InputForm inputForm) {
    	return playerRepository.save(inputForm);
    }

    public Optional<InputForm> getLastUserInput(int id) {
        return playerRepository.findById(id);
    }

	

}
