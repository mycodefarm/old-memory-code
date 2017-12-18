package com.jimo.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimo.model.User;
import com.jimo.service.UserService;
import com.jimo.util.Result;
import com.jimo.util.ResultHelper;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService us;

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 */
	@GetMapping("/getCode")
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		int width = 100, height = 30;// 图片宽高
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 创建图像缓冲区
		Graphics g = bi.getGraphics(); // 通过缓冲区创建一个画布
		Color c = new Color(250, 250, 255); // 创建颜色
		g.setColor(c);// 为画布创建背景颜色
		g.fillRect(0, 0, width, height);

		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random r = new Random();
		int len = ch.length;
		int index; // index用于存放随机数字
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(len);// 产生随机数字
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255))); // 设置颜色
			g.drawString(ch[index] + "", (i * (width / 4)) + 10, 20);// 画数字以及数字的位置
			sb.append(ch[index]);
		}
		request.getSession().setAttribute("piccode", sb.toString()); // 将数字保留在session中，便于后续的使用
		try {
			ImageIO.write(bi, "JPG", response.getOutputStream());
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/login")
	public Result login(User user, String code, HttpSession session) {
		String c = (String) session.getAttribute("piccode");
		if (code == null || !code.toUpperCase().equals(c)) {
			return ResultHelper.error("验证码错误");
		}
		return us.login(user, session);
	}

	@PostMapping("/exit")
	public Result exit(HttpSession session) {
		session.removeAttribute("user");
		return ResultHelper.success();
	}
}
