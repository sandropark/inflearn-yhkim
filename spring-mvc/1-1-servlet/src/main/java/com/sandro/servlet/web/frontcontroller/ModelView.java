package com.sandro.servlet.web.frontcontroller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelView {
    private String viewName;
    private final Map<String, Object> model = new HashMap<>();
}
