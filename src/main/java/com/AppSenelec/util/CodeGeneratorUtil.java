package com.AppSenelec.util;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;

@Component
public class CodeGeneratorUtil {
    
    private final SecureRandom random = new SecureRandom();
    
    /**
     * Génère un code unique avec paramètres configurables
     * @param length Longueur du code
     * @param useLetters Utiliser des lettres
     * @param useNumbers Utiliser des chiffres
     * @param codeExistsChecker Vérificateur d'unicité
     * @return Code unique
     */
    public String generateUniqueCode(int length, boolean useLetters, boolean useNumbers, 
                                   java.util.function.Predicate<String> codeExistsChecker) {
        String code;
        do {
            code = generateCode(length, useLetters, useNumbers);
        } while (codeExistsChecker.test(code));
        return code;
    }
    
    /**
     * Génère un code de recharge unique de 20 chiffres (pour compatibilité)
     */
    public String generateUniqueCode(java.util.function.Predicate<String> codeExistsChecker) {
        return generateUniqueCode(20, false, true, codeExistsChecker);
    }
    
    /**
     * Génère un code avec paramètres configurables
     * @param length Longueur du code
     * @param useLetters Utiliser des lettres
     * @param useNumbers Utiliser des chiffres
     * @return Code généré
     */
    public String generateCode(int length, boolean useLetters, boolean useNumbers) {
        StringBuilder sb = new StringBuilder();
        String chars = "";
        
        if (useLetters) chars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (useNumbers) chars += "0123456789";
        
        if (chars.isEmpty()) chars = "0123456789"; // Par défaut
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * Génère un code de recharge de 20 chiffres (pour compatibilité)
     */
    public String generateCode() {
        return generateCode(20, false, true);
    }
    
    /**
     * Génère un code alphanumérique (lettres + chiffres)
     */
    public String generateAlphanumericCode(int length) {
        return generateCode(length, true, true);
    }
    
    /**
     * Génère un code avec seulement des lettres
     */
    public String generateLetterCode(int length) {
        return generateCode(length, true, false);
    }
} 