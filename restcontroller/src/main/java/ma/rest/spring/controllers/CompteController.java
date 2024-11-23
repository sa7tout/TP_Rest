package ma.rest.spring.controllers;

import ma.rest.spring.entities.Compte;
import ma.rest.spring.entities.CompteList;
import ma.rest.spring.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque")
public class CompteController {

    @Autowired
    private CompteRepository compteRepository;

    // READ: Récupérer tous les comptes (JSON)
    @GetMapping(value = "/comptes", produces = "application/json")
    public List<Compte> getAllComptesJson() {
        return compteRepository.findAll();
    }

    // READ: Récupérer tous les comptes (XML)
    @GetMapping(value = "/comptes", produces = "application/xml")
    public CompteList getAllComptesXml() {
        List<Compte> comptes = compteRepository.findAll();
        CompteList compteList = new CompteList();
        compteList.setComptes(comptes);
        return compteList;
    }

    // READ: Récupérer un compte par son identifiant (JSON et XML)
    @GetMapping(value = "/comptes/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        return compteRepository.findById(id)
                .map(compte -> ResponseEntity.ok().body(compte))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Ajouter un nouveau compte (JSON et XML)
    @PostMapping(value = "/comptes", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public Compte createCompte(@RequestBody Compte compte) {
        return compteRepository.save(compte);
    }

    // UPDATE: Mettre à jour un compte existant (JSON et XML)
    @PutMapping(value = "/comptes/{id}", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compteDetails) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setSolde(compteDetails.getSolde());
                    compte.setDateCreation(compteDetails.getDateCreation());
                    compte.setType(compteDetails.getType());
                    Compte updatedCompte = compteRepository.save(compte);
                    return ResponseEntity.ok().body(updatedCompte);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Supprimer un compte
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compteRepository.delete(compte);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}