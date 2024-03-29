import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/blog/*")
public class blog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public blog() {
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
		PrintWriter out = response.getWriter();

		blogService blogService = new blogService();
		BlogDTO BlogDTO = new BlogDTO();
		List<BlogDTO> BlogList = new ArrayList<BlogDTO>();

		int blogId = 0;
		String action = "";
		if (!request.getPathInfo().equals("/image/favicon.png")) {
			action = request.getPathInfo();
			String[] actionSeg = action.split("/");
			action = (String)actionSeg[1];
			
			if (actionSeg.length == 3) {
				blogId = Integer.parseInt(actionSeg[2]);
			}
		}
		
		if (action.equals("writeBlog.do")) {
			HttpSession session = request.getSession();
			String loginID = (String) session.getAttribute("id");
			String loginName = (String) session.getAttribute("name");

			if (loginID != null) {
				BlogDTO.setWriter(loginName);
				BlogDTO.setTitle(request.getParameter("title"));
				BlogDTO.setContents(request.getParameter("contents"));
				BlogDTO.setWriterID(loginID);

				blogService.addBlog(BlogDTO);

				out.print("<script>alert('새 글이 등록되었습니다.'); location.href='");
				out.print(request.getContextPath());
				out.print("';</script>");
			} else {
				out.print("<script>alert('로그인이 필요합니다.'); location.href='");
				out.print(request.getContextPath());
				out.print("/login.jsp';</script>");
			}
		}

		if (action.equals("blogList.do")) {
			int page = 1;
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			if (request.getParameter("search") != null) {
				String search = request.getParameter("search");
				BlogList = blogService.getBlogList(10, page, true, search);
			} else {
				BlogList = blogService.getBlogList(10, page, true, null);
			}

			request.setAttribute("page", page);
			request.setAttribute("BlogList", BlogList);
			request.setAttribute("search", request.getParameter("search"));
			request.getRequestDispatcher("/blogList.jsp").forward(request, response);
		}
		
		if (action.equals("myBlogList.do")) {
			HttpSession session = request.getSession();
			String loginID = (String) session.getAttribute("id");
			
			if (loginID != null) {
				int page = 1;
				if (request.getParameter("page") != null) {
					page = Integer.parseInt(request.getParameter("page"));
				}
				if (request.getParameter("search") != null) {
					String search = request.getParameter("search");
					BlogList = blogService.getmyBlogList(10, page, true, loginID, search);
				} else {
					BlogList = blogService.getmyBlogList(10, page, true, loginID, null);
				}

				request.setAttribute("page", page);
				request.setAttribute("BlogList", BlogList);
				request.setAttribute("search", request.getParameter("search"));
				request.getRequestDispatcher("/myBlogList.jsp").forward(request, response);
			} else {
				out.print("<script>alert('로그인이 필요합니다.'); location.href='");
				out.print(request.getContextPath());
				out.print("/login.jsp';</script>");
			}
		}
		
		if (action.equals("viewBlog")) {
			BlogDTO.setBlogNum(blogId);
			BlogDTO = blogService.getBlogInfo(BlogDTO);

			request.setAttribute("BlogDTO", BlogDTO);
			request.getRequestDispatcher("/viewBlog.jsp").forward(request, response);
		}
		
		if (action.equals("updateBlog.do")) {
			BlogDTO.setBlogNum(Integer.parseInt(request.getParameter("blogNum")));
			BlogDTO.setTitle(request.getParameter("title"));
			BlogDTO.setContents(request.getParameter("contents"));
		
			blogService.updateBlog(BlogDTO);
			out.print("<script>alert('수정이 완료되었습니다.'); location.href='");
			out.print(request.getContextPath());
			out.print("/blog/myBlogList.do';</script>");
		}
		
		if (action.equals("deleteBlog")) {
			HttpSession session = request.getSession();
			String loginID = (String) session.getAttribute("id");

			if (loginID != null) {
				BlogDTO.setBlogNum(blogId);
				BlogDTO = blogService.getBlogInfo(BlogDTO);

				if (loginID.equals(BlogDTO.getWriterID())) {
					blogService.deleteBlog(BlogDTO);

					
					out.print("<script>alert('글이 삭제되었습니다.'); window.location=document.referrer;</script>");
				} else {
					out.print("<script>alert('글 작성자만 삭제할 수 있습니다.'); location.href='");
					out.print(request.getContextPath());
					out.print("/blog/blogList.do';</script>");
				}
			} else {
				out.print("<script>alert('로그인이 필요합니다.'); location.href='");
				out.print(request.getContextPath());
				out.print("/login.jsp';</script>");
			}
		}
		
		if (action.equals("updateBlogReq")) {
			HttpSession session = request.getSession();
			String loginID = (String) session.getAttribute("id");
			
			if (loginID != null) {
				BlogDTO.setBlogNum(blogId);
				BlogDTO = blogService.getBlogInfo(BlogDTO);
				String writerID = BlogDTO.getWriterID();
				System.out.println(writerID);

				if (loginID.equals(BlogDTO.getWriterID())) {
					request.setAttribute("BlogDTO", BlogDTO);
					request.getRequestDispatcher("/updateBlog.jsp").forward(request, response);
				} else {
					out.print("<script>alert('글 작성자만 수정할 수 있습니다.'); location.href='");
					out.print(request.getContextPath());
					out.print("/blog/blogList.do';</script>");
				}
			} else {
				out.print("<script>alert('로그인이 필요합니다.'); location.href='");
				out.print(request.getContextPath());
				out.print("/login.jsp';</script>");
			}
		}
	}
}