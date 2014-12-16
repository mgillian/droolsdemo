package net.unicon.ccc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan(basePackages = "net.unicon.ccc")
@EnableAutoConfiguration()
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
