package br.com.gunna.basemvvmrxproject.androidapp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 06/12/17.
 */

public class ValidationUtils {


    private final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    public static  boolean isValidEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

    private static boolean IsValidCNPJ(String cnpj) {
        return !(cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
                (cnpj.length() != 14)) && IsValid(cnpj, new Integer[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}, 14);
    }

    public static boolean IsValidCPF(String cpf) {
        return !(cpf.equalsIgnoreCase("00000000000") || cpf.equalsIgnoreCase("11111111111") || cpf.equalsIgnoreCase("22222222222") || cpf.equalsIgnoreCase("33333333333")
                || cpf.equalsIgnoreCase("44444444444") || cpf.equalsIgnoreCase("55555555555") || cpf.equalsIgnoreCase("66666666666")
                || cpf.equalsIgnoreCase("77777777777") || cpf.equalsIgnoreCase("88888888888") || cpf.equalsIgnoreCase("99999999999") || cpf.equalsIgnoreCase("00000000000")) && IsValid(cpf, new Integer[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2}, 11);
    }

    private static boolean IsValid(String cpfCnpj, Integer[] mult, int length) {
        if (cpfCnpj.length() != length)
            return false;

        String cpnjWithoutCheckDigit = cpfCnpj.substring(0, length - 2);
        List<Integer> ints = Arrays.asList(mult);
        int sum = Sum(cpnjWithoutCheckDigit, ints.subList(1, ints.size()));

        Integer rest = sum % 11;
        rest = rest < 2 ? 0 : 11 - rest;

        String digits = rest.toString();
        cpnjWithoutCheckDigit += digits;
        sum = Sum(cpnjWithoutCheckDigit, ints);

        rest = sum % 11;
        rest = rest < 2 ? 0 : 11 - rest;
        digits += rest.toString();
        return cpfCnpj.endsWith(digits);
    }

    private static int Sum(String cpnjWithoutCheckDigit, List<Integer> mult) {
        int sum = 0;

        char[] chars = cpnjWithoutCheckDigit.toCharArray();

        List<Character> listC = new ArrayList<>();
        for (char c : chars) {
            listC.add(c);
        }
        for (int i = 0; i < listC.size(); i++) {
            sum += Integer.parseInt(listC.get(i).toString()) * mult.get(i);
        }
        return sum;
    }


}