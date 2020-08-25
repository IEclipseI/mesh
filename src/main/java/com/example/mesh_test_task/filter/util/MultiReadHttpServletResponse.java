package com.example.mesh_test_task.filter.util;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MultiReadHttpServletResponse extends HttpServletResponseWrapper {
    public MultiReadHttpServletResponse(HttpServletResponse response) {
        super(response);
    }


    public static class CachedServletOutputStream extends ServletInputStream {
        private final ByteArrayInputStream buffer;

        public CachedServletOutputStream(byte[] contents) {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}