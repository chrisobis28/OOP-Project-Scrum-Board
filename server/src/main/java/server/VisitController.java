package server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class VisitController {
    private VisitService vs;

    public VisitController(VisitService vs){
        this.vs = vs;
    }
    @GetMapping("/{address}")
    @ResponseBody
    public String index(@PathVariable("address") String address ) {
        vs.increase();
        return "You are number " + vs.value() ;
    }

}