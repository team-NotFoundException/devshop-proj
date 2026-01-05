package org.example.shopping._core.utils;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ValidationUtils {

    private ValidationUtils() {}

    public static boolean redirectIfError(
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            String redirectUrl
    ) {
        if (!bindingResult.hasErrors()) return false;

        redirectAttributes.addFlashAttribute(
                "alertMessage",
                bindingResult.getAllErrors().get(0).getDefaultMessage()
        );
        return true;
    }
}
