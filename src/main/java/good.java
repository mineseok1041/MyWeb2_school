import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/good")
public class good extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public good() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		goodService goodService = new goodService();
		goodDTO goodDTO = new goodDTO();
		
		goodService.goodup();
		int goodCount = goodService.getGoodCount().getGood();
		
		response.getWriter().print(goodCount);
	}
}