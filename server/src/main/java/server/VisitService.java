package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@Service
public class VisitService {
	private int i = 0;
	public void increase() {
		i++;
	}

	public int value(){
		return i;
	}

}
