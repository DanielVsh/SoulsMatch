package com.danielvishnievskyi.soulsmatch;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.RegisterRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.*;
import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import com.danielvishnievskyi.soulsmatch.repository.*;
import com.danielvishnievskyi.soulsmatch.service.auth.AuthenticationService;
import com.danielvishnievskyi.soulsmatch.service.profile.ProfileService;
import com.danielvishnievskyi.soulsmatch.service.user.SoulService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
public class SoulsMatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(SoulsMatchApplication.class, args);
  }

//  @Bean
//  public CommandLineRunner commandLineRunner(
//    ProfileRepository profileRepository,
//    SoulService soulService,
//    AuthenticationService authenticationService,
//    ProfileService profileService,
//    SoulRepository soulRepository,
//    LocationRepository locationRepository,
//    CityRepository cityRepository,
//    CountryRepository countryRepository
//  ) {
//    return args -> {
//
//
//      IntStream.range(0, 20000).forEach(operand ->
//        authenticationService.register(new RegisterRequestDto(
//          "user" + operand + "@gmail.com",
//          "123",
//          "user" + operand,
//          "user" + operand,
//          Gender.FEMALE,
//          "22-10-2002")));
//
//      City kosice = cityRepository.save(new City(null, "Kosice", 48.71395, 21.25808));
//
//      City presov = cityRepository.save(new City(null, "Presov", 48.99839, 21.23393));
//
//      City bratislava = cityRepository.save(new City(null, "Bratislava", 48.14816, 17.10674));
//
//      Country slovakia = countryRepository.save(new Country(null, "Slovakia", List.of(kosice, presov, bratislava)));
//
//      IntStream.range(0, 20000).forEach(operand ->
//        profileRepository.save( new Profile(
//          null,
//          soulRepository.findById(Integer.toUnsignedLong(operand) + 1).orElseThrow(),
//          "description " + operand,
//          List.of(),
//          List.of(Gender.MALE, Gender.FEMALE)
//        )));
//
//      Location locKosice = locationRepository.save(new Location(null, slovakia, kosice));
//      Location locPresov = locationRepository.save(new Location(null, slovakia, presov));
//      Location locBratislava = locationRepository.save(new Location(null, slovakia, bratislava));
//
//      List<Location> locations = locationRepository.findAll();
//
//
//      Random random = new Random();
//      soulRepository.saveAll(soulRepository.findAll().stream()
//        .peek(soul -> soul.setLocation(locations.get(random.nextInt(3))))
//        .toList());
//    };
//  }
}
