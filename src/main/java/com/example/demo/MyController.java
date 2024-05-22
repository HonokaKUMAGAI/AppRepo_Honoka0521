package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("formValidation")
public class MyController {

	@Autowired
	MyService myService;
	
	@Autowired
    WeatherService weatherService;

	@Autowired
	PlayerRepository playerRepository;

	@GetMapping("/")
	public String getIndex(Model model, HttpSession session) {
		FormValidation formValidation = (FormValidation) session.getAttribute("formValidation");
		if (formValidation == null) {
			formValidation = new FormValidation();
		}

		model.addAttribute("title", "あなたの設定を入力しよう！");
		model.addAttribute("formValidation", new FormValidation());
		return "index";
	}

	@PostMapping("/pre_start")
	public String postForm(@Validated FormValidation formValidation,
			BindingResult bindingResult, Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("pre_title", "あなたの設定をまだまだ入力しよう！");
			model.addAttribute("formValidation", formValidation);
			return "index";
		}

		session.setAttribute("formValidation", formValidation);
		model.addAttribute("pre_title", "これでよろしいですか？");
		model.addAttribute("formValidation", formValidation);
		//		model.addAttribute("inputFormId", savedInputForm.getId());
		return "pre_start";
	}

	@PostMapping("/")
	public String gobackIndex(Model model, HttpSession session) {

		FormValidation formValidation = (FormValidation) session.getAttribute("formValidation");
		if (formValidation == null) {
			formValidation = new FormValidation();
		}
		model.addAttribute("title", "あなたの設定を入力しよう！（手直し画面）");
		model.addAttribute("formValidation", formValidation);
		return "index";
	}

	@PostMapping("/start")
	public String postStart(@Validated FormValidation formValidation, Model model, HttpSession session) {

		session.setAttribute("formValidation", formValidation);
		// データベースにフォームの内容を保存
		InputForm inputForm = new InputForm();
		inputForm.setName(formValidation.getName());
		inputForm.setAge(formValidation.getAge());
		inputForm.setFavCity(formValidation.getFavCity());
		inputForm.setFavFood(formValidation.getFavFood());
		inputForm.setHobby(formValidation.getHobby());

		// データベースに保存
		myService.saveUserInput(inputForm);

//		List<InputForm> inputForms = playerRepository.findAll();
//		model.addAttribute("inputForms", inputForms);

		String name = ((FormValidation) session.getAttribute("formValidation")).getName();
		model.addAttribute("name", name);

		model.addAttribute("title", "さあ、キミの冒険がはじまるよ！");//代替テキスト
		return "start";
	}

	@GetMapping("/kakunin")
	public String showAllData(Model model) {
		List<InputForm> inputForms = playerRepository.findAll();
		model.addAttribute("inputForms", inputForms);
		return "kakunin";
	}
	

	private final String apiKey = "6b97b55fcf184e42e6c05a6dda5c4815"; // ここにOpenWeatherAPIのAPIキーを入力してください
	
//	@GetMapping("/story")
//	public String getWeather(Model model, HttpSession session) {
//		String favCity = ((FormValidation) session.getAttribute("formValidation")).getFavCity();
//		model.addAttribute("favCity", favCity);
//		
//		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + favCity + "&appid=" + apiKey;
//	    RestTemplate restTemplate = new RestTemplate();
//	    WeatherData weatherData = restTemplate.getForObject(apiUrl, WeatherData.class);
//	    
//	    String weatherDescription = weatherData.getWeather()[0].getMain(); // 天気の状態
//	    double temperature = weatherData.getMain().getTemp(); // 気温（ケルビン単位）
//	    double humidity = weatherData.getMain().getHumidity(); // 湿度（パーセンテージ）
//	    
//	    // 気温を摂氏（℃）に変換
//	    double temperatureCelsius = temperature - 273.15;
//
//	    // モデルに天気情報をセットしてstory.htmlに渡す
//	    model.addAttribute("weatherDescription", weatherDescription);
//	    model.addAttribute("temperature", temperatureCelsius);
//	    model.addAttribute("humidity", humidity);
//
//        return "story";
//	}
	
	@GetMapping("/story")
    public String getWeather2(Model model, HttpSession session) {
        String favCity = ((FormValidation) session.getAttribute("formValidation")).getFavCity();
        model.addAttribute("favCity", favCity);
        
        // WeatherServiceを使って天気情報を取得
        WeatherData weatherData = weatherService.getWeatherData(favCity);
        
        // 天気情報をモデルにセット
        if(weatherData != null) {
            String weatherDescription = weatherData.getWeather()[0].getMain();
            double temperature = weatherData.getMain().getTemp() - 273.15; // Kelvin to Celsius
            double humidity = weatherData.getMain().getHumidity();
            model.addAttribute("weatherDescription", weatherDescription);
            model.addAttribute("temperature", temperature);
            model.addAttribute("humidity", humidity);
        } else {
            // 天気情報が取得できなかった場合の処理
            model.addAttribute("weatherDescription", "N/A");
            model.addAttribute("temperature", "N/A");
            model.addAttribute("humidity", "N/A");
        }
        
        String name = ((FormValidation) session.getAttribute("formValidation")).getName();
		model.addAttribute("name", name);

        return "story";
    }

}
