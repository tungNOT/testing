package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class TelephoneManipulateApp {
	public static Map<String, Object> responseMap = new HashMap<>();
	public int numOfLetters;
	private int totalManipulations;
	private int pageSize = 100;
	private int pageNumber;
	public static void main(String[] args) {
		SpringApplication.run(TelephoneManipulateApp.class, args);
	}

	@RequestMapping("/manipulate")
	public Map<String, Object> manipulateNumber(@RequestParam(value="telephone") String telephone, @RequestParam(value="page") int page) {
		if(!telephone.isEmpty()){
			clearList();
			this.pageNumber = page;
			numOfLetters = telephone.length();
			scrambleTelephone(telephone, "");
			responseMap.put("page", getPageSet());
			responseMap.put("total", totalManipulations);
		}
		return responseMap;
	}

	private void clearList(){
		totalManipulations = 0;
		listOfTelephones = new HashSet<>();
	}

	private Set<String> listOfTelephones = new HashSet<>();

	private void scrambleTelephone(String telephone, String prefix){
                // the base case is when we reach the end of telephone length the prefix will contains one combination
                // check if combination is unique. This is done because of the hashset doesn't allow duplicate
                if (telephone.length() == 0 ){
                    // add the combination to the hashset if the combination is a duplicate it will not be added
                    listOfTelephones.add(prefix);
                }
                else {
                    // looping through all digits to recursively building the sequence combinations
                    for (int i = 0; i < telephone.length(); i++){
                        // recursively passing the string of new added telephone digits to the prefix and taking out the added digit out of the telephone
                        // this will be recursively call until the prefix is full and it will backtrack to the previous digit and so on and so fort.
                        scrambleTelephone( telephone.substring(0,i) + telephone.substring(i+1, telephone.length()), prefix + telephone.charAt(i));
                    }
                }
                // The total combinations should be the size of hashset because there are not duplicate
                totalManipulations = listOfTelephones.size();
                
                
	}
        
       
    private static final Logger LOG = Logger.getLogger(TelephoneManipulateApp.class.getName());

	private ArrayList<String> getPageSet(){
		int start = (this.pageNumber-1) * 100 ;
		int end = this.pageNumber * 100 > listOfTelephones.size() ? listOfTelephones.size() : this.pageNumber * 100;
		ArrayList<String> list = new ArrayList<>(listOfTelephones);
		list.addAll(listOfTelephones);
		ArrayList<String> set = new ArrayList<>(list.subList(start, end));
		return set;
	}
}
