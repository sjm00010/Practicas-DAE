/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author sjm00010
 */
@EnableAutoConfiguration
public class ServidorLogistica {
    
    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(ServidorLogistica.class);
        ApplicationContext context = servidor.run(args);
    }
}
