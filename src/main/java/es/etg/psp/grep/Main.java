package es.etg.psp.grep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Main {

    public static final String MSG_ERROR = "Se ha producido un error al ejecutar el comando";
    public static final String[] COMANDO_GREP = { "grep", "PSP" };
    public static final String[] LINEAS_ENTRADA = {
            "Me gusta PSP y java",
            "PSP se programa en java",
            "es un módulo de DAM",
            "y se programa de forma concurrente en PSP",
            "PSP es programación."
    };

    public static void main(String[] args) {
        try {
            Process proceso = Runtime.getRuntime().exec(COMANDO_GREP);

            OutputStream outputStream = proceso.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

            for (String linea : LINEAS_ENTRADA) {
                writer.println(linea);
            }

            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));

            StringBuilder output = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                output.append(linea).append("\n");
            }
            reader.close();

            int exitVal = proceso.waitFor();
            if (exitVal == 0) {
                System.out.println(output.toString());
                System.exit(0);
            } else {
                System.out.println(MSG_ERROR);
                System.exit(1);
            }

        } catch (IOException | InterruptedException e) {
            System.exit(34);
        }
    }
}
