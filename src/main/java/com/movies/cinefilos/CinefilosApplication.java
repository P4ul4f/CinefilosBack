package com.movies.cinefilos;

import com.movies.cinefilos.Entities.Permission;
import com.movies.cinefilos.Entities.Role;
import com.movies.cinefilos.Entities.RoleEnum;
import com.movies.cinefilos.Entities.User;
import com.movies.cinefilos.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CinefilosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinefilosApplication.class, args);
	}

	/*@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args -> {
			//CREATE PERMISSIONS
			Permission createPermission = Permission.builder()
					.name("CREATE")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Permission readPermission = Permission.builder()
					.name("READ")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Permission updatePermission = Permission.builder()
					.name("UPDATE")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Permission deletePermission = Permission.builder()
					.name("DELETE")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Permission refactorPermission = Permission.builder()
					.name("REFACTOR")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			//CREATE ROLES
			Role roleAdmin = Role.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Role roleUser = Role.builder()
					.roleEnum(RoleEnum.USER)
					.permissions(Set.of(readPermission, createPermission))
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Role roleInvited = Role.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissions(Set.of(readPermission))
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			Role roleDeveloper = Role.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissions(Set.of(readPermission, createPermission, updatePermission, deletePermission, refactorPermission))
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.build();

			//CREATE USERS
			User userPaula = User.builder()
					.username("Paula")
					.password("$2a$10$LHFMiYEVeu0c2z9zz1PxpeaoQHyEO1TksyIsUV3/1H7qOAEkpa7da")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();

			User userLorenzo = User.builder()
					.username("Lorenzo")
					.password("$2a$10$LHFMiYEVeu0c2z9zz1PxpeaoQHyEO1TksyIsUV3/1H7qOAEkpa7da")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			User userInvited = User.builder()
					.username("Invitado")
					.password("$2a$10$LHFMiYEVeu0c2z9zz1PxpeaoQHyEO1TksyIsUV3/1H7qOAEkpa7da")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.roles(Set.of(roleInvited))
					.build();

			User userManuel = User.builder()
					.username("Manuel")
					.password("$2a$10$LHFMiYEVeu0c2z9zz1PxpeaoQHyEO1TksyIsUV3/1H7qOAEkpa7da")
					.registrationDate(LocalDate.now())
					.desactivationDate(null)
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.roles(Set.of(roleUser))
					.build();

			userRepository.saveAll(List.of(userPaula, userManuel, userLorenzo, userInvited));
		};*/
	/*}*/

}
