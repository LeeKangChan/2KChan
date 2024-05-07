package com.chan.demo.common;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

public class AlertUtil {
		public final static void returnAlert(Model model, String msg, String type, String url) {
			model.addAttribute("msg", msg);
			model.addAttribute("url", type);
			model.addAttribute("type", url);
		}
		
		public final static void returnAlert(ModelMap model, String msg, String type, String url) {
			model.addAttribute("msg", msg);
			model.addAttribute("url", type);
			model.addAttribute("type", url);
		}
	}
