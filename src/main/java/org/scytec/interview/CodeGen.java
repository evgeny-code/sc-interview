package org.scytec.interview;

import org.jooq.codegen.GenerationTool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class CodeGen {
    public static void main(String[] args) throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./db/db;INIT=RUNSCRIPT FROM './db/create.sql'")) {}

        GenerationTool.generate(
                Files.readString(
                        Path.of(ClassLoader.getSystemResource("jooq-config.xml").toURI())
                )
        );
    }
}
