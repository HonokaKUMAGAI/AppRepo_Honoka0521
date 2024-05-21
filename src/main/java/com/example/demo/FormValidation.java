package com.example.demo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FormValidation {

	@Size(min=1, max=20, message="1～20文字にしてください" )
	private String name = "";
	
	@Min(value = 0, message="0以上の年齢を入力してください")
	private String age = "";
	
	@NotBlank(message = "選択してください")
    private String favCity;
	
	@Size(min=1, max=20, message="1～20文字にしてください" )
	private String favFood = "";

	@Size(min=1, max=20, message="1～20文字にしてください" )
	private String hobby = "";
	
}
