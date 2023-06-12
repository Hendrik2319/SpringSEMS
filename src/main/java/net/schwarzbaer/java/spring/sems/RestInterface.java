package net.schwarzbaer.java.spring.sems;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestInterface {

	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/demo/hello")
	public Message hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Message(counter.incrementAndGet(), String.format("Hello %s!", name));
	}

	public record Message(long id, String text) {}
}
