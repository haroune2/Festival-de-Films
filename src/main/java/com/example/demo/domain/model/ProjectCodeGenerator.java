package com.example.demo.domain.model;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
 
public class ProjectCodeGenerator {

    private static final String POM_FILE_NAME = "pom.xml";
    private static final String MODEL_MAPPER_DEPENDENCY = "<dependency>\n" +
            "    <groupId>org.modelmapper</groupId>\n" +
            "    <artifactId>modelmapper</artifactId>\n" +
            "    <version>3.2.0</version>\n" +
            "</dependency>";

    public static void main(String[] args) {
        // Obtenez le chemin du répertoire de travail (l'emplacement du générateur de code)
        String workingDir = System.getProperty("user.dir");

        // Construisez le chemin du fichier pom.xml
        String pomFilePath = workingDir + FileSystems.getDefault().getSeparator() + POM_FILE_NAME;

        // Ajoutez la dépendance ModelMapper à la fin de la section <dependencies>
        addModelMapperDependencyToPom(pomFilePath);
        createConfig(pomFilePath, pomFilePath, pomFilePath, pomFilePath); 
    }

    private static void createConfig(String code, String suffix, String className, String packagePath) {
 
            String directoryPath = "src/main/java/" + packagePath.replace('.', '/');
            new File(directoryPath).mkdirs();
    
            try (PrintWriter writer = new PrintWriter(new FileWriter(directoryPath + "/" + className + suffix + ".java"))) {
                writer.println(code);
                System.out.println("Code de " + className + suffix + " généré avec succès dans " + directoryPath + "/"+ className + suffix + ".java");
            } catch (IOException e) {
                e.printStackTrace();
            }
 
    }

    private static void addModelMapperDependencyToPom(String pomFilePath) {
        try {
            // Lisez le contenu actuel du fichier pom.xml
            Path path = Paths.get(pomFilePath);
            int dependenciesEndIndex = -1 ; 
            List<String> lines = Files.readAllLines(path);
             for (String line : lines) {
                System.out.println("line : "+line);
                if(line.contains("<dependencies>")){
                    dependenciesEndIndex = lines.indexOf(line)+1; 
                }
            }

            // Trouvez l'index de la balise de fermeture </dependencies>
             System.out.println("dependenciesEndIndex : "+dependenciesEndIndex);

    
            if (dependenciesEndIndex != -1) {
                // Ajoutez la dépendance ModelMapper juste avant la balise de fermeture </dependencies>
                lines.add(dependenciesEndIndex, MODEL_MAPPER_DEPENDENCY);
            } else {
                // Si la balise de fermeture </dependencies> n'est pas trouvée, ajoutez la dépendance à la fin du fichier

                lines.add(MODEL_MAPPER_DEPENDENCY);
            }

            // Écrivez le contenu modifié dans le fichier pom.xml
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
