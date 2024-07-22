package org.ippolito.andrea.anagram_utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@Slf4j
@SpringBootApplication
@CommandScan
public class AnagramUtilsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AnagramUtilsApplication.class, args);
  }

}
