import java.io.*;
import java.util.*;

// Class representing the Macro Processor
public class Pass_Macro {
    // Define tables used in Pass 1
    private static Map<String, MacroDefinition> macroTable = new HashMap<>();
    private static List<String> intermediateCode = new ArrayList<>();

    public static void main(String[] args) {
        // Get source program from file
        List<String> sourceProgram = readSourceProgramFromFile("sourceProgram.txt");

        // Perform Pass 1 to define macros
        pass1(sourceProgram);

        // Perform Pass 2 to expand macros
        pass2();

        // Output the final expanded code
        System.out.println("Expanded Code:");
        intermediateCode.forEach(System.out::println);
    }

    // Function to read source program from a file
    private static List<String> readSourceProgramFromFile(String fileName) {
        List<String> sourceProgram = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sourceProgram.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return sourceProgram;
    }

    // Pass 1: Define macros and populate the macro table
    private static void pass1(List<String> sourceProgram) {
        boolean inMacroDefinition = false;
        MacroDefinition currentMacro = null;

        for (String line : sourceProgram) {
            String[] tokens = line.trim().split("\\s+");

            if (tokens[0].equals("MACRO")) {
                inMacroDefinition = true;
                continue;
            } else if (tokens[0].equals("MEND")) {
                inMacroDefinition = false;
                macroTable.put(currentMacro.name, currentMacro);
                continue;
            }

            if (inMacroDefinition) {
                if (currentMacro == null) {
                    String name = tokens[0];
                    List<String> parameters = Arrays.asList(tokens[1].split(","));
                    currentMacro = new MacroDefinition(name, parameters);
                } else {
                    currentMacro.body.add(line);
                }
            } else {
                intermediateCode.add(line); // Add non-macro lines directly
            }
        }
    }

    // Pass 2: Expand macros based on the macro table
    private static void pass2() {
        List<String> expandedCode = new ArrayList<>();

        for (String line : intermediateCode) {
            String[] tokens = line.trim().split("\\s+");

            if (macroTable.containsKey(tokens[0])) {
                MacroDefinition macro = macroTable.get(tokens[0]);
                List<String> actualParameters = Arrays.asList(tokens[1].split(","));
                expandedCode.addAll(macro.expand(actualParameters));
            } else {
                expandedCode.add(line); // Add non-macro lines directly
            }
        }

        intermediateCode = expandedCode; // Update intermediate code with expanded code
    }

    // Class to define a Macro with parameters and body
    static class MacroDefinition {
        String name;
        List<String> parameters;
        List<String> body;

        public MacroDefinition(String name, List<String> parameters) {
            this.name = name;
            this.parameters = parameters;
            this.body = new ArrayList<>();
        }

        // Expand the macro by replacing formal parameters with actual parameters
        public List<String> expand(List<String> actualParameters) {
            List<String> expandedLines = new ArrayList<>();

            for (String line : body) {
                String expandedLine = line;

                for (int i = 0; i < parameters.size(); i++) {
                    expandedLine = expandedLine.replace(parameters.get(i), actualParameters.get(i));
                }

                expandedLines.add(expandedLine);
            }

            return expandedLines;
        }
    }
}
