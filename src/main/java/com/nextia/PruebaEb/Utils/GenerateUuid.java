package com.nextia.PruebaEb.Utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class GenerateUuid {
    public String generateUuid(){
        UUID generate = Generators.timeBasedGenerator().generate();
        String uuid = generate.toString();
        return uuid;
    }
}
