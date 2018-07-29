//package com.pl.skijumping.domain.utils;
//
///**
// * Created by Zyzio on 06.10.2017.
// */
//
//public final class QuerryPattern {
//
//    private QuerryPattern() {}
//
//    public static String getQuerry(Integer rank, Integer bib, Integer fis_code, String name, String surname, String nationality) {
//
//        StringBuilder querry = new StringBuilder();
//        if (checkInt(rank)) querry.append("rank = " + rank);
//        if (checkInt(bib)) querry.append((querry.toString().equals("") ? "" : " AND ") + "bib = " + bib);
//        if (checkInt(fis_code)) querry.append((querry.toString().equals("") ? "" : " AND ") + "fis_code = " + fis_code);
//        if (checkString(name)) querry.append((querry.toString().equals("") ? "" : " AND ") + "name = '" + name+"'");
//        if (checkString(surname)) querry.append((querry.toString().equals("") ? "" : " AND ") + "surname = '" + surname +"'");
//        if (checkString(nationality))
//            querry.append((querry.toString().equals("") ? "" : " AND ") + "nationality = '" + nationality +"'");
//
//        return querry.toString();
//    }
//
//    public static boolean checkInt(Integer value) {
//        if (value == null) return false;
//        if (value > 0) return true;
//        else return false;
//    }
//
//    public static boolean checkString(String value) {
//        if (value != null && !value.equals("") && !value.equals(" ")) return true;
//        else return false;
//    }
//}
