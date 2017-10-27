/**
 * 
 */
package vm.web.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import vm.db.common.Configurations;

/**
 * @author Administrator
 *
 */
public class LoginFilter implements Filter {
	
	private List<String> whiteList = null;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			String urlWhiteList = Configurations.getInstance().getProperty("path.url_white_list");
			InputStream in = this.getClass().getResourceAsStream("/" + urlWhiteList);
			this.whiteList = IOUtils.readLines(new InputStreamReader(in));
		} catch (Exception e) {
			this.whiteList = new ArrayList<String>();
		}
	}
		
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();
		for (String white : this.whiteList) {
			if (uri.indexOf(white) > 0) {
				chain.doFilter(req, resp);
				return ;
			}
		}
		HttpSession session = request.getSession();
		if (session != null) {
			if (session.getAttribute("user") != null) {
				chain.doFilter(req, resp);
				return ;
			}
		}
		String contextPath = ((HttpServletRequest) req).getServletContext().getContextPath();
		((HttpServletResponse) resp).sendRedirect(contextPath + "/login.html");
	}
	
	@Override
	public void destroy() {
		
	}

}
