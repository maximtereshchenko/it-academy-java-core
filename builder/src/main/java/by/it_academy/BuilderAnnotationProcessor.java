package by.it_academy;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class BuilderAnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("by.it_academy.Builder");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .map(element -> (ExecutableElement) element)
                .forEach(this::writeBuilderClass);
        return true;
    }

    private String setterMethodName(String variableName) {
        return "with" + Character.toUpperCase(variableName.charAt(0)) + variableName.substring(1);
    }

    private void writeBuilderClass(ExecutableElement constructor) {
        try {
            JavaFileObject builderClass = javaFile(constructor);
            writeBuilderClass(constructor, builderClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeBuilderClass(ExecutableElement constructor, JavaFileObject javaFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(javaFile.openWriter())) {
            writePackageName(writer, constructor);
            writeClassDefinition(writer, constructor);
        }
    }

    private void writeClassDefinition(PrintWriter writer, ExecutableElement constructor) {
        writeClassDeclaration(writer, constructor);
        writer.println('{');
        constructor.getParameters().forEach(parameter -> writeFieldAndSetterMethod(writer, parameter, constructor));
        writeBuildMethod(writer, constructor);
        writer.println('}');
    }

    private void writeBuildMethod(PrintWriter writer, ExecutableElement constructor) {
        writer.print("public ");
        writer.print(targetClassName(constructor));
        writer.println(" build(){");
        writer.print("return new ");
        writer.print(targetClassName(constructor));
        writer.print('(');
        writer.write(parameters(constructor));
        writer.println(");");
        writer.println("}");
    }

    private String parameters(ExecutableElement constructor) {
        return constructor.getParameters()
                .stream()
                .map(this::variableName)
                .collect(Collectors.joining(","));
    }

    private void writeFieldAndSetterMethod(PrintWriter writer, VariableElement variable, ExecutableElement constructor) {
        writeField(writer, variable);
        writeSetterMethod(writer, variable, constructor);
    }

    private void writeSetterMethod(PrintWriter writer, VariableElement variable, ExecutableElement constructor) {
        writer.print("public ");
        writer.print(builderClassName(constructor));
        writer.print(' ');
        writer.print(setterMethodName(variableName(variable)));
        writer.print('(');
        writer.print(variableType(variable));
        writer.print(' ');
        writer.print(variableName(variable));
        writer.println("){");
        writer.print("this.");
        writer.print(variableName(variable));
        writer.print('=');
        writer.print(variableName(variable));
        writer.println(';');
        writer.println("return this;");
        writer.println("}");
    }

    private String variableType(VariableElement variable) {
        return variable.asType().toString();
    }

    private String variableName(VariableElement variable) {
        return variable.getSimpleName().toString();
    }

    private void writeField(PrintWriter writer, VariableElement variable) {
        writer.print("private ");
        writer.print(variableType(variable));
        writer.print(' ');
        writer.print(variableName(variable));
        writer.println(';');
    }

    private void writeClassDeclaration(PrintWriter writer, ExecutableElement constructor) {
        writer.print("public class ");
        writer.print(builderClassName(constructor));
    }

    private void writePackageName(PrintWriter writer, ExecutableElement constructor) {
        writer.print("package ");
        writer.print(packageName(constructor));
        writer.println(';');
    }

    private JavaFileObject javaFile(ExecutableElement constructor) throws IOException {
        return processingEnv.getFiler()
                .createSourceFile(packageName(constructor) + '.' + builderClassName(constructor));
    }

    private String packageName(ExecutableElement constructor) {
        String targetClassName = targetClassName(constructor);
        return targetClassName.substring(0, targetClassName.lastIndexOf('.'));
    }

    private String builderClassName(ExecutableElement constructor) {
        String targetClassName = targetClassName(constructor);
        return targetClassName.substring(targetClassName.lastIndexOf('.') + 1) + "Builder";
    }

    private String targetClassName(ExecutableElement constructor) {
        return constructor.getEnclosingElement().toString();
    }
}
