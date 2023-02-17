package com.sandro.servlet.web.frontcontroller.v5.adaptor;

import com.sandro.servlet.web.frontcontroller.ModelView;
import com.sandro.servlet.web.frontcontroller.v4.ControllerV4;
import com.sandro.servlet.web.frontcontroller.v5.MyHandlerAdaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdaptor implements MyHandlerAdaptor {

    @Override
    public Boolean supports(Object handler) {
        return handler instanceof ControllerV4;
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = new ModelView();
        String viewName = controller.process(paramMap, mv.getModel());
        mv.setViewName(viewName);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName ->
                paramMap.put(paramName, request.getParameter(paramName))
        );
        return paramMap;
    }
}
