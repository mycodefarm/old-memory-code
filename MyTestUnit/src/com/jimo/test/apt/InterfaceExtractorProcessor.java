package com.jimo.test.apt;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Created by jimo on 17-8-27.
 */
@SupportedAnnotationTypes({"com.jimo.test.apt.ExtractInterface"})
public class InterfaceExtractorProcessor extends AbstractProcessor {

    private ProcessingEnvironment env;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        env = processingEnvironment;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = env.getMessager();
        for (TypeElement te : set) {
            for (Element e : roundEnvironment.getElementsAnnotatedWith(te)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "Print: " + e.toString());
            }
        }
        return true;
    }
}
