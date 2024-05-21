package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

	@Autowired
	MyService myService;

	@Autowired
	PlayerRepository playerRepository;

	@GetMapping("/")
	public String getIndex(Model model) {
		model.addAttribute("title", "あなたの設定を入力しよう！");
		model.addAttribute("formValidation", new FormValidation());
		return "index";
	}

	@PostMapping("/pre_start")
	public String postForm(@Validated FormValidation formValidation,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("pre_title", "あなたの設定をまだまだ入力しよう！");
			model.addAttribute("formValidation", formValidation);
			return "index";
		}

		InputForm inputForm = new InputForm();
		inputForm.setName(formValidation.getName());
		inputForm.setAge(formValidation.getAge());
		inputForm.setFavCity(formValidation.getFavCity());
		inputForm.setFavFood(formValidation.getFavFood());
		inputForm.setHobby(formValidation.getHobby());

		InputForm savedInputForm = myService.saveUserInput(inputForm);

		model.addAttribute("pre_title", "これでよろしいですか？");
		model.addAttribute("formValidation", formValidation);
		model.addAttribute("inputFormId", savedInputForm.getId());
		return "pre_start";
	}

	@PostMapping("/")
	public String gobackIndex(Model model) {
		Optional<InputForm> lastInput = playerRepository.findFirstByOrderByIdDesc();

		if (lastInput.isPresent()) {
			// IDが存在する場合、そのIDを元に入力内容を取得してフォームに設定
			model.addAttribute("formValidation", lastInput.get());
		} else {
			// IDが存在しない場合、新しいフォームを作成
			model.addAttribute("formValidation", new FormValidation());
		}

		return "index";
	}

	@PostMapping("/start")
	public String postStart(Model model) {
		Optional<InputForm> lastInputOptional = playerRepository.findFirstByOrderByIdDesc();
		if (lastInputOptional.isPresent()) {
		    InputForm lastInput = lastInputOptional.get();
		    String name = lastInput.getName();
		    model.addAttribute("name", name);
		}
		model.addAttribute("title", "さあ、キミの冒険がはじまるよ！");
		return "start";
	}
	
	
	@GetMapping("/kakunin")
    public String showAllData(Model model) {
        List<InputForm> inputForms = playerRepository.findAll();
        model.addAttribute("inputForms", inputForms);
        return "kakunin";
    }

}
