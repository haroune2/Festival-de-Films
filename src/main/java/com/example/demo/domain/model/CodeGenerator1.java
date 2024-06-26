package com.example.demo.domain.model;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.List;

public class CodeGenerator1 {
    public static void main(String[] args) {
        // Spécifiez le package contenant vos classes
        String basePackageModel = "com.example.demo.domain.model";
        String basePackage = "com.example.demo";

        // Liste des noms de classe à générer
        List<String> classNames = Arrays.asList("Etudiant", "Skil", "Room", "Adress");

        // Générez les classes de contrôleur, service, DTO, repository et exception
        generateClasses(classNames, basePackage, basePackageModel);
    }

    private static void generateClasses(List<String> classNames, String basePackage, String basePackageModel) {
        for (String className : classNames) {
            generateController(className, basePackage);
            generateService(className, basePackage);
            generateDTO(className, basePackage);
            generateRepository(className, basePackage);
            generateExceptionClass(className, basePackage);
            generateEntity(className, basePackageModel);
        }
        // Générez ModelMapperConfig s'il n'existe pas
        generateModelMapperConfig(basePackage);
    }

    private static void generateModelMapperConfig(String basePackage) {
        String configurationFolderPath = "src/main/java/" + basePackage.replace('.', '/') + "/configuration";
        createConfigurationFolder(configurationFolderPath);

        String modelMapperConfigFilePath = configurationFolderPath + FileSystems.getDefault().getSeparator()+ "ModelMapperConfig.java";

        try {
            // Vérifiez si le fichier ModelMapperConfig.java existe déjà
            Path path = Paths.get(modelMapperConfigFilePath);

            if (!Files.exists(path)) {
                // Si le fichier n'existe pas, créez-le et écrivez le contenu
                Files.write(path, getModelMapperConfigContent().getBytes());
                System.out.println("Le fichier ModelMapperConfig.java a été généré avec succès.");
            } else {
                System.out.println("Le fichier ModelMapperConfig.java existe déjà.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createConfigurationFolder(String folderPath) {
        Path path = Paths.get(folderPath);
    
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Dossier de configuration créé avec succès.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le dossier de configuration existe déjà.");
        }
    }
    

    private static String getModelMapperConfigContent() {
        return "package com.example.demo.configuration;\n\n" +
                "import org.modelmapper.ModelMapper;\n" +
                "import org.springframework.context.annotation.Bean;\n" +
                "import org.springframework.context.annotation.Configuration;\n\n" +
                "@Configuration\n" +
                "public class ModelMapperConfig {\n" +
                "    \n" +
                "    @Bean\n" +
                "    public ModelMapper modelMapper(){\n" +
                "        return new ModelMapper();\n" +
                "    }\n" +
                "}\n";
    }

    private static void generateEntity(String className, String basePackageModel) {
        String entityPackage = basePackageModel;
        String entityCode = String.format(
                "package %s;\n\n" +
                        "import jakarta.persistence.Entity;\n" +
                        "import jakarta.persistence.Id;\n\n" +
                        "@Entity\n" +
                        "public class %s {\n" +
                        "    @Id\n" +
                        "    private Long id;\n\n" +
                        "    public %s() {\n" +
                        "        // Constructeur sans paramètre\n" +
                        "    }\n" +
                        "}\n",
                entityPackage, className, className);

        saveCodeToFile(entityCode, "", className, basePackageModel);
    }

    private static void generateExceptionClass(String className, String basePackage) {
        String entityName = className.substring(className.lastIndexOf('.') + 1);
        String exceptionClassName = entityName + "Exception";

        String exceptionCode = "package " + basePackage + ".domain.exception;\n\n" +
                "import org.springframework.http.HttpStatus;\n\n" +
                "public class " + exceptionClassName + " extends RuntimeException {\n\n" +
                "    private HttpStatus statusCode;\n\n" +
                "    public " + exceptionClassName + "(HttpStatus statusCode, String message) {\n" +
                "        super(message);\n" +
                "        this.statusCode = statusCode;\n" +
                "    }\n\n" +
                "    public HttpStatus getStatusCode() {\n" +
                "        return statusCode;\n" +
                "    }\n\n" +
                "    public void setStatusCode(HttpStatus statusCode) {\n" +
                "        this.statusCode = statusCode;\n" +
                "    }\n" +
                "}\n";

        String directoryPath = "src/main/java/" + basePackage.replace('.', '/') + "/domain/exception";
        new File(directoryPath).mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(directoryPath + "/" + exceptionClassName + ".java"))) {
            writer.println(exceptionCode);
            System.out.println("Code de " + exceptionClassName + " généré avec succès dans " + directoryPath + "/"
                    + exceptionClassName + ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void generateController(String className, String basePackage) {
        String entityName = className.substring(className.lastIndexOf('.') + 1);
        String controllerCode = "package " + basePackage + ".controller;\n\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import java.util.List;\n" +
                "import " + basePackage + ".service." + entityName + "Service;\n" +
                "import " + basePackage + ".domain.dto." + entityName + "DTO;\n" +
                "import " + basePackage + ".domain.model." + entityName + ";\n\n" +
    
                "/**\n" +
                " * CONTROLLER " + entityName.toUpperCase() + " - LOGIQUE CRUD\n" +
                " *\n" +
                " *  /" + entityName.toLowerCase() + "s -> GET : list of " + entityName.toLowerCase() + "s\n" +
                " *  /" + entityName.toLowerCase() + "s/{id} -> GET : get a " + entityName.toLowerCase() + " by ID\n" +
                " *  /" + entityName.toLowerCase() + "s -> POST : create a new " + entityName.toLowerCase() + "\n" +
                " *  /" + entityName.toLowerCase() + "s/{id} -> PUT : update a " + entityName.toLowerCase() + " by ID\n" +
                " *  /" + entityName.toLowerCase() + "s/{id} -> DELETE : delete a " + entityName.toLowerCase() + " by ID\n" +
                " */\n" +
                "@RestController\n" +
                "@RequestMapping(\"/" + entityName.toLowerCase() + "s\")\n" +
                "public class " + entityName + "Controller {\n\n" +
    
                "    @Autowired\n" +
                "    private " + entityName + "Service " + entityName.toLowerCase() + "Service;\n\n" +
    
                "    /**\n" +
                "     * POST\n" +
                "     * Créer une nouvelle entité.\n" +
                "     */\n" +
                "    @PostMapping\n" +
                "    public ResponseEntity<" + entityName + "DTO> create"  + "(@RequestBody " + entityName
                + " " + entityName.toLowerCase() + ") {\n" +
                "        " + entityName + "DTO created" + entityName + "DTO = " + entityName.toLowerCase()
                + "Service.create" + entityName + "(" + entityName.toLowerCase() + ");\n" +
                "        return ResponseEntity.ok(created" + entityName + "DTO);\n" +
                "    }\n\n" +
    
                "    /**\n" +
                "     * GET\n" +
                "     * Récupérer la liste de tous les " + entityName.toLowerCase() + "s.\n" +
                "     */\n" +
                "    @GetMapping\n" +
                "    public ResponseEntity<List<" + entityName + "DTO>> list" + "() {\n" +
                "        List<" + entityName + "DTO> all" + entityName + "DTOs = " + entityName.toLowerCase()
                + "Service.getAll" + entityName + "s();\n" +
                "        return ResponseEntity.ok(all" + entityName + "DTOs);\n" +
                "    }\n\n" +
    
                "    /**\n" +
                "     * GET\n" +
                "     * Récupérer une entité par ID.\n" +
                "     */\n" +
                "    @GetMapping(\"/{id}\")\n" +
                "    public ResponseEntity<" + entityName + "DTO> get" +"(@PathVariable Long id) {\n"
                +
                "        " + entityName + "DTO found" + entityName + "DTO = " + entityName.toLowerCase() + "Service.get"
                + entityName + "ById(id).orElse(null);\n" +
                "        return ResponseEntity.ok(found" + entityName + "DTO);\n" +
                "    }\n\n" +
    
                "    /**\n" +
                "     * PUT\n" +
                "     * Mettre à jour une entité.\n" +
                "     */\n" +
                "    @PutMapping(\"/{id}\")\n" +
                "    public ResponseEntity<" + entityName + "DTO> update" 
                + "(@PathVariable Long id, @RequestBody " + entityName + " " + entityName.toLowerCase() + ") {\n" +
                "        " + entityName + "DTO updated" + entityName + "DTO = " + entityName.toLowerCase()
                + "Service.update" + entityName + "(id, " + entityName.toLowerCase() + ");\n" +
                "        return ResponseEntity.ok(updated" + entityName + "DTO);\n" +
                "    }\n\n" +
    
                "    /**\n" +
                "     * DELETE\n" +
                "     * Supprimer une entité.\n" +
                "     */\n" +
                "    @DeleteMapping(\"/{id}\")\n" +
                "    public ResponseEntity<Void> delete"  + "(@PathVariable Long id) {\n" +
                "        " + entityName.toLowerCase() + "Service.delete" + entityName + "(id);\n" +
                "        return ResponseEntity.noContent().build();\n" +
                "    }\n" +
    
                "}\n";
    
        saveCodeToFile(controllerCode, "Controller", entityName, basePackage + "/controller");
    }
    

    private static void generateService(String className, String basePackage) {
        String entityName = className.substring(className.lastIndexOf('.') + 1);
        String repositoryName = entityName + "Repository";
        String serviceCode = "package " + basePackage + ".service;\n\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import " + basePackage + ".domain.model." + entityName + ";\n" +
                "import " + basePackage + ".domain.dto." + entityName + "DTO;\n" +
                "import " + basePackage + ".repository." + repositoryName + ";\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import java.util.List;\n" +
                "import java.util.Optional;\n" +
                "import org.modelmapper.ModelMapper;\n\n" +

                "@Service\n" +
                "public class " + entityName + "Service {\n\n" +

                "    @Autowired\n" +
                "    private " + repositoryName + " " + entityName.toLowerCase() + "Repository;\n\n" +

                "    @Autowired\n" +
                "    private ModelMapper modelMapper;\n\n" +

                "    public " + entityName + "DTO create" + entityName + "(" + entityName + " "
                + entityName.toLowerCase() + ") {\n" +
                "        " + entityName + " created" + entityName + " = " + entityName.toLowerCase()
                + "Repository.save(" + entityName.toLowerCase() + ");\n" +
                "        return modelMapper.map(created" + entityName + ", " + entityName + "DTO.class);\n" +
                "    }\n\n" +

                "    public List<" + entityName + "DTO> getAll" + entityName + "s() {\n" +
                "        List<" + entityName + "> all" + entityName + "s = " + entityName.toLowerCase()
                + "Repository.findAll();\n" +
                "        return convertListToDTOs(all" + entityName + "s);\n" +
                "    }\n\n" +

                "    public Optional<" + entityName + "DTO> get" + entityName + "ById(Long id) {\n" +
                "        Optional<" + entityName + "> entity = " + entityName.toLowerCase()
                + "Repository.findById(id);\n" +
                "        return entity.map(e -> modelMapper.map(e, " + entityName + "DTO.class));\n" +
                "    }\n\n" +

                "    public " + entityName + "DTO update" + entityName + "(Long id, " + entityName + " "
                + entityName.toLowerCase() + ") {\n" +
                "        if (!" + entityName.toLowerCase() + "Repository.existsById(id)) {\n" +
                "            return null; // Gérer le cas où l'entité n'existe pas\n" +
                "        }\n" +
                "        " + entityName + " updated" + entityName + " = " + entityName.toLowerCase()
                + "Repository.save(" + entityName.toLowerCase() + ");\n" +
                "        return modelMapper.map(updated" + entityName + ", " + entityName + "DTO.class);\n" +
                "    }\n\n" +

                "    public void delete" + entityName + "(Long id) {\n" +
                "        " + entityName.toLowerCase() + "Repository.deleteById(id);\n" +
                "    }\n\n" +

                "    private List<" + entityName + "DTO> convertListToDTOs(List<" + entityName + "> entities) {\n" +
                "        return entities.stream()\n" +
                "                .map(e -> modelMapper.map(e, " + entityName + "DTO.class))\n" +
                "                .toList();\n" +
                "    }\n" +

                "}\n";

        saveCodeToFile(serviceCode, "Service", entityName, basePackage + "/service");
    }

    private static void generateDTO(String className, String basePackage) {
        String entityName = className.substring(className.lastIndexOf('.') + 1);

        String dtoCode = "package " + basePackage + ".domain.dto;\n\n" +
                "// Ajoutez les champs et méthodes nécessaires pour le DTO\n\n" +
                "public class " + entityName + "DTO {\n\n" +
                "}\n";

        saveCodeToFile(dtoCode, "DTO", entityName, basePackage + "/domain/dto");
    }

    private static void generateRepository(String className, String basePackage) {
        String entityName = className.substring(className.lastIndexOf('.') + 1);

        String repositoryCode = "package " + basePackage + ".repository;\n\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n\n" +
                "import org.springframework.stereotype.Repository;\n\n" +
                "import " + basePackage + ".domain.model." + entityName + ";\n" +

                "@Repository\n" +
                "public interface " + entityName + "Repository extends JpaRepository<" + entityName + ", Long> {\n\n" +
                "    // Ajoutez les méthodes personnalisées du repository\n\n" +
                "}\n";

        saveCodeToFile(repositoryCode, "Repository", entityName, basePackage + "/repository");
    }

    private static void saveCodeToFile(String code, String suffix, String className, String packagePath) {
        String directoryPath = "src/main/java/" + packagePath.replace('.', '/');
        new File(directoryPath).mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(directoryPath + "/" + className + suffix + ".java"))) {
            writer.println(code);
            System.out.println("Code de " + className + suffix + " généré avec succès dans " + directoryPath + "/"
                    + className + suffix + ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}