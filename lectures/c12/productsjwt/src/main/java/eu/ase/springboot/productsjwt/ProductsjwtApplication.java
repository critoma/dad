/*
package eu.ase.springboot.productsjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsjwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsjwtApplication.class, args);
	}

}
*/

package eu.ase.springboot.productsjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@RestController
public class ProductsjwtApplication {
   public static void main(String[] args) {
      SpringApplication.run(ProductsjwtApplication.class, args);
   }
   @RequestMapping(value = "/products")
   public String getProductName() {
      return "Honey";   
   }
}
