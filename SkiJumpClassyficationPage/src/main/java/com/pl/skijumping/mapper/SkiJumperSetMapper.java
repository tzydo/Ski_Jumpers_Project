//package com.pl.ski_jumping.mapper;
//
//import com.pl.skijumping.entity.SkiJumper;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.validation.BindException;
//
//
//public class SkiJumperSetMapper implements FieldSetMapper<SkiJumper> {
//
//    private  SkiJumper skiJumper;
//    @Override
//    public SkiJumper mapFieldSet(FieldSet fieldSet) throws BindException {
//
//        try{
//           skiJumper = SkiJumper.builder()
//                    .rank(fieldSet.readInt("rank"))
//                    .bib(fieldSet.readInt("bib"))
//                    .fis_code(fieldSet.readInt("fis_code"))
//                    .name(fieldSet.readString("name"))
//                    .surname(fieldSet.readString("surname"))
//                    .nationality(fieldSet.readString("nationality"))
//                    .first_jump(fieldSet.readInt("first_jump"))
//                    .points_for_first_jump(fieldSet.readInt("points_for_first_jump"))
//                    .second_jump(fieldSet.readInt("second_jump"))
//                    .points_for_second_jump(fieldSet.readInt("points_for_second_jump"))
//                    .total_points(fieldSet.readInt("total_points"))
//                    .build();
//        }catch (Exception ex){
//            skiJumper = SkiJumper.builder()
//                    .rank(fieldSet.readInt("rank"))
//                    .bib(fieldSet.readInt("bib"))
//                    .fis_code(fieldSet.readInt("fis_code"))
//                    .name(fieldSet.readString("name"))
//                    .surname(fieldSet.readString("surname"))
//                    .nationality(fieldSet.readString("nationality"))
//                    .first_jump(fieldSet.readInt("first_jump"))
//                    .points_for_first_jump(fieldSet.readInt("points_for_first_jump"))
//                    .second_jump(0)
//                    .points_for_second_jump(0)
//                    .total_points(fieldSet.readInt("total_points"))
//                    .build();
//        }
//
//
//        return skiJumper;
//    }
//}