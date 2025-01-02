package com.eurotec.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eurotec.backend.entity.Boutique;
import com.eurotec.backend.entity.Produit;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>
{
	
	List<Produit> findByBoutiqueId(Long id);	
	
	@Query(value = """
					SELECT p 
					 FROM Produit p 
					 where (p.boutique.id = :id) 
					 and ( p.nom like %:search% or p.codeBarre like %:search% ) 
				""")
	List<Produit> rechercherCommande(@Param("search") String search , @Param("id") Long id , Pageable page);
	

	@Query(value = """
				SELECT p 
				FROM Produit p 
				where (p.boutique.id = :id) 
				and ( p.nom like %:search% or p.codeBarre like %:search% ) 
				
			""")
	List<Produit> rechercherSimple(@Param("search") String search , @Param("id") Long id , Pageable page);
	
	
	@Query(value = """
			SELECT p
			 FROM Produit p join p.boutique b
			 where ( p.nom like %:search% or p.codeBarre like %:search%  ) 
			 and b.id = 17
		""")
	List<Produit> rechercherTout(@Param("search") String search  , Pageable page);
		
	@Query(value = """
			SELECT p
			 FROM Produit p join p.boutique b
			 where ( p.famille like :search ) 
			 and b.id = 17
		""")
	List<Produit> rechercherParCategorie(@Param("search") String search  , Pageable page);
	
	@Query(value = """
			SELECT p
			 FROM Produit p join p.boutique b
			 where ( p.nouveaute = true  ) 
			 and b.id = 17
		""")
	List<Produit> rechercherNouveaute( Pageable page);
	
	
	@Query(value = """
			SELECT p
			 FROM Produit p join p.boutique b
			 where ( p.promotion = true  ) 
			 and b.id = 17
		""")
	List<Produit> rechercherPromotion( Pageable page);
	
	
	@Query(value = """
			SELECT p
			 FROM Produit p join p.boutique b
			 where ( p.retourEnStock = true  ) 
			 and b.id = 17
		""")
	List<Produit> rechercherRetourEnStock( Pageable page);
	
	
	
	@Query(value = """
			SELECT DISTINCT p.famille
			 FROM Produit p join p.boutique b
			where (p.famille is not null) and (p.famille <> '') 
			and b.id = 17
		""")
	List<String> rechercherCategorie();
		

}