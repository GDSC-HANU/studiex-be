package com.gdsc.studyex;

import com.gdsc.studyex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studyex.domain.supply_and_demand.services.allowed_supply.CreateAllowedSupplyService;
import com.gdsc.studyex.infrastructure.supply_and_demand.repositories.AllowedSupplyMongoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudiexApplication {

	public static void main(String[] args) {;
		SpringApplication.run(StudiexApplication.class, args);
	}

}
