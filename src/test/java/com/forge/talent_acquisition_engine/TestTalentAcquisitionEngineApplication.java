package com.forge.talent_acquisition_engine;

import com.forge.talentAcquisitionEngine.TalentAcquisitionEngineApplication;
import org.springframework.boot.SpringApplication;

public class TestTalentAcquisitionEngineApplication {

	public static void main(String[] args) {
		SpringApplication.from(TalentAcquisitionEngineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
