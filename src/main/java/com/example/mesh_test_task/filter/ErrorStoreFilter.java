package com.example.mesh_test_task.filter;

import com.example.mesh_test_task.domain.ApiError;
import com.example.mesh_test_task.service.ApiErrorService;
import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

@Component
@Order(1)
public class ErrorStoreFilter extends GenericFilterBean {
    private final ApiErrorService apiErrorService;

    public ErrorStoreFilter(ApiErrorService apiErrorService) {
        this.apiErrorService = apiErrorService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(body);

        chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) {
            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return new DelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), ps)
                );
            }
            @Override
            public  PrintWriter getWriter() throws IOException {
                return new PrintWriter(new DelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), ps))
                );
            }
        });
        if (((HttpServletResponse) response).getStatus() >= 400 && !body.toString().isEmpty()) {
            ApiError apiError = new ApiError();
            apiError.setMsg(body.toString());
            apiErrorService.save(apiError);
        }
    }
}
