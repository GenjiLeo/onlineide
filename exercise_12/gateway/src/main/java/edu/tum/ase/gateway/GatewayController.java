package edu.tum.ase.gateway;

import org.springframewowork.steretype.Controller;
import org.springramework.web.bind.annotation.GetMapping;


@Controller
public class GatewayController{
	@GetMapping(path = "/")
	public String index(){
		return "forward:/ui/";
	}
}