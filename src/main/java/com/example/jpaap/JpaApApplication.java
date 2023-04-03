package com.example.jpaap;

import com.example.jpaap.entities.Patient;
import com.example.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner  {
    public static void main(String[] args) {
        SpringApplication.run(JpaApApplication.class, args);
    }
    @Autowired
    private PatientRepository patientRepository;
    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"Houda",new Date(),false,84));

        patientRepository.save(new Patient(null,"Ali",new Date(),true,50));

        patientRepository.save(new Patient(null,"Asmaa",new Date(),false,70));

        for(int i=0;i<100;i++){
            patientRepository.save(new Patient(null,"Houda",new Date(),false,(int)(Math.random()*10)));
        }

        Page<Patient> patients= patientRepository.findAll(PageRequest.of(4,5));
        System.out.println("Total pages :" + patients.getTotalPages());
        System.out.println("Total element :" + patients.getTotalElements());

        //List<Patient> byMalade=patientRepository.findByMalade(true);
        Page<Patient> byMalade=patientRepository.findByMalade(false,PageRequest.of(0,4));
        System.out.println(byMalade);
        patients.forEach(p->{
            System.out.println("*****************");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });
        System.out.println("-------------------");
        Patient patient=patientRepository.findById(1L).orElse(null);
        if(patient!=null) {
            System.out.println(patient);
            patient.setScore(500);
            patientRepository.save(patient);
            System.out.println(patient);
        }
        else {
            System.out.println("non existant !");
        }
        patientRepository.deleteById(3L);
    }
}
