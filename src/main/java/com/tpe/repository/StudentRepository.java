package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
        SORU : @Repository veya @Service yerine @Component kullanirsam ne olur ??

        CEVAP : @Repository ve @Service, ozellestirilmis @Component olarak dizayn edilmistir,
        bundaki amaclari soyle siralayabilirz :
	*	Kod okunabilirligi artirmak
	*	Ilgili katmana has exception mekanizmasinin devreye girmesini saglamak
	Mesela @Repository katmaninda bir uncheckedException olusursa ;
	   PersistenceExceptionTranslationPostProcessor = Spring Framework'ün bir parçası olarak
	   gelen ve özellikle veritabanı işlemleri sırasında ortaya çıkan istisnaları ele almayı
	   amaçlayan bir mekanizmadır. Bu mekanizma, unchecked exception türünden
	   istisnaları yakalar ve bu istisnaları Spring'in veritabanı işlemleri sırasında daha
	   spesifik bir şekilde ele alabileceği belirli istisnalar haline dönüştürür. ( unchecked
	   exception)  turunde exception firlamasina gore Spring kendini dizayn eder.

 */

@Repository // Opsiyonel , kod okunabilirligi
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    List<Student> findByLastName(String lastName);


    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    // yukardaki JPQL i Native SQL ile yazarsak :
    @Query(value = "SELECT * FROM Student s WHERE s.grade=:pGrade", nativeQuery = true)
    List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);
}